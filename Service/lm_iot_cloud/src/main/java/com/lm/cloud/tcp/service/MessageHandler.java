package com.lm.cloud.tcp.service;

import com.alibaba.fastjson2.JSON;
import com.lm.admin.entity.dto.device.DeviceNewDataDto;
import com.lm.admin.service.device.DeviceServiceImpl;
import com.lm.admin.service.devicedata.DeviceDataServiceImpl;
import com.lm.admin.utils.LmAssert;
import com.lm.cloud.common.auth.DeviceAuth;
import com.lm.admin.entity.dto.device.DeviceAllDataDto;
import com.lm.cloud.common.r.CloudDeviceConnRespEnum;
import com.lm.cloud.common.r.CloudDevicePushAckEnum;
import com.lm.cloud.common.r.CloudR;
import com.lm.cloud.tcp.service.utils.DeviceCmdUtils;
import com.lm.cloud.tcp.service.utils.RedisDeviceUtils;
import com.lm.common.redis.devicekey.CloudRedisKey;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


/**
 * 消息处理,单例启动
 *
 * @author qiding
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class MessageHandler extends SimpleChannelInboundHandler<String>   {
    @Autowired
    private DeviceAuth deviceAuth;

    @Autowired
    private DeviceServiceImpl deviceService;
    @Autowired
    private DeviceDataServiceImpl deviceDataService;

    @Autowired
    private StringRedisTemplate redisTemplate;  // 操作Redis

    //group充当业务线程池，可以将任务提交到该线程池
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(16);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        log.info("成功建立连接,channelId：{}", ctx.channel().id().asLongText());
        super.channelActive(ctx);
    }
    // 笔记
    // channelRead 和 channelRead0 的区别
    // channelRead很明显做了一个消息类型检查 channelRead0是直接获取
    // 总结：
    // channelRead 中调用了 channelRead0，其会先做消息类型检查，判断当前message 是否需要传递到下一个handler。
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        if (JSON.isValid(message)) {
            // 用于查询对应的sn码 如果id存在sn码 就代表鉴权过了
            String dbr_SnByChannelId = RedisDeviceUtils.getSnByCid(ctx.channel().id().asLongText());
            Integer t = JSON.parseObject(message).getInteger("t");
            // 查询是否授权 如果根据id查不到sn码就表示未鉴权
            if (LmAssert.isEmpty(dbr_SnByChannelId)) {
                // 只有t才能发起鉴权
                if (t == 1) {
                    log.info("测试用途:开始授权");
                    // 设备鉴权 不能放异步线程
                    ctx.writeAndFlush(JSON.toJSONString(deviceAuth.TcpAuth(ctx, message)));
                    return;
                }
                // 未授权 请发起授权信息
                log.info("测试用途:未授权 请发起授权信息");
                ctx.writeAndFlush(JSON.toJSONString(CloudR.Response(CloudDeviceConnRespEnum.CONNECT_ERROR_NO_AUTH)));

//                ctx.writeAndFlush("测试用途:未授权 请发起授权信息");
                return;
            } else {
                if(t == 3 ){
                    // 设备数据上报数据量大 数据库操作耗时较高  所以放到异步线程里面去做。 为什么不直接开启业务线程嘞？这样的话不管你处理什么任务都开个线程不太灵活。
                    // log.info("group.submit 异步执行的线程："+Thread.currentThread().getName());
                    // 接收到设备最新数据
                    DeviceNewDataDto deviceDataUpRo = JSON.parseObject(message, DeviceNewDataDto.class);
                    deviceDataUpRo.setSn(dbr_SnByChannelId);
                    // log.info("序列化后--->>>>{}", deviceDataUpRo);
                    group.submit(()->{
                        // 保存数据 放到tdengine
                        int a = deviceDataService.saveDeviceData(dbr_SnByChannelId,deviceDataUpRo.getData());
                    });
                    // 保存数据到redis
                    boolean b = deviceDataService.saveDeviceDataRedis(deviceDataUpRo);
                    if(b){
                        // 接收数据成功
                        ctx.writeAndFlush(JSON.toJSONString(CloudR.Response(CloudDevicePushAckEnum.DATA_PUSH_ACK_SUCCESS)));
                    }
                    else{
                        // 接收数据失败
                        ctx.writeAndFlush(JSON.toJSONString(CloudR.Response(CloudDevicePushAckEnum.DATA_PUSH_ACK_ERROR)));
                    }
                }
                else if(t == 6){
                    // 客户端响应服务器的CMD命令执行状态
                    // 序列化设备的数据
                    DeviceAllDataDto deviceDataUpRo = JSON.parseObject(message, DeviceAllDataDto.class);
                    log.info("序列化后--->>>>{}", deviceDataUpRo);
                    DeviceCmdUtils.responseCmd(deviceDataUpRo,dbr_SnByChannelId);
                }
            }
        } else {
            ctx.writeAndFlush("测试用途:请使用JSON格式发送");
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("10秒内未读到信息--->{}", ctx.channel().id().asLongText());
        ctx.channel().close(); //关闭连接
    }

    // https://blog.csdn.net/qq_28497823/article/details/106191187 生命周期
    // channelInactive handlerRemoved 二选一去处理关闭连接或者异常断开连接的事务逻辑就行
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // https://blog.csdn.net/lalalahaitang/article/details/81561553
        log.info("channelInactive 断开连接,channelId：{}", ctx.channel().id().asLongText());
        // 后面只处理正常连接成功的设备，进行断开连接
        // 通过channelId查询到设备的sn码，找到sn删除key channelId删除key
//        String findSnBycId = redisTemplate.opsForValue().get("lmCloud:cloud:tcp:channelId:"+ctx.channel().id().toString());
        // 根据id查询sn码
        String db_SnBycId = RedisDeviceUtils.getSnByCid(ctx.channel().id().asLongText());
        if(LmAssert.isNotEmpty(db_SnBycId)){

            String db_channelIdKey = CloudRedisKey.ChannelIdToDeviceSnKey + ctx.channel().id().asLongText();
            String db_deviceSnKey = CloudRedisKey.DeviceSnToChannelIdKey + db_SnBycId;
            // 删除key
            redisTemplate.delete(db_channelIdKey);
            redisTemplate.delete(db_deviceSnKey);

            // 设备在线计数器减一
            RedisDeviceUtils.setDeviceOnLineCount(false);
//            redisTemplate.opsForValue().increment(CloudRedisKey.DeviceOnLineCount, -1); // 设置递增减因子
            // 通过id移除对应的值
            RedisDeviceUtils.deviceMap.remove(ctx.channel().id().asLongText());

        }


    }
//    "lmCloud:cloud:tcp:channelId:"
//    redisTemplate.opsForValue().set("lmCloud:cloud:tcp:channelId:"+ctx.channel().id().toString(),tcp_ret_deviceSn);


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved 断开连接,channelId：{}", ctx.channel().id());
        super.handlerRemoved(ctx);
    }
}
