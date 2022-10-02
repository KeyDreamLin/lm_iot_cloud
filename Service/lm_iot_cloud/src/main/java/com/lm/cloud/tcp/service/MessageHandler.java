package com.lm.cloud.tcp.service;

import com.alibaba.fastjson2.JSON;
import com.lm.admin.service.device.IDeviceService;
import com.lm.admin.tool.JSONUtils;
import com.lm.admin.tool.LmAssert;
import com.lm.cloud.common.auth.DeviceAuth;
import com.lm.admin.entity.dto.device.DeviceDataDto;
import com.lm.cloud.common.r.CloudDeviceConnRespEnum;
import com.lm.cloud.common.r.CloudDevicePushAckEnum;
import com.lm.cloud.common.r.CloudR;
import com.lm.cloud.common.r.CloudRedisKey;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
    private IDeviceService deviceService;
    @Autowired
    private StringRedisTemplate redisTemplate;  // 操作Redis

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("\n");
        log.info("成功建立连接,channelId：{}", ctx.channel().id().asLongText());
        super.channelActive(ctx);
    }

    // 笔记
    // channelRead 和 channelRead0 的区别
    // channelRead很明显做了一个消息类型检查 channelRead0是直接获取
    // 总结：
    // channelRead 中调用了 channelRead0，其会先做消息类型检查，判断当前message 是否需要传递到下一个handler。
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        // 用于查询对应的sn码 如果id存在sn密码 就代表鉴权过了
        String dbr_SnByChannelId = redisTemplate.opsForValue().get(CloudRedisKey.ChannelIdToDeviceSnKey + ctx.channel().id().asLongText());
        log.info("--->>>>{}",dbr_SnByChannelId);
        if (JSONUtils.isJSONValidate(message)) {
            Integer t = JSON.parseObject(message).getInteger("t");
            // 查询是否授权 如果根据id查不到sn码就表示未鉴权
            if (LmAssert.isEmpty(dbr_SnByChannelId)) {
                // 只有t才能发起鉴权
                if (t == 1) {
                    ctx.writeAndFlush(JSON.toJSONString(deviceAuth.TcpAuth(ctx, message)));
                    log.info("测试用途:开始授权");
                    return;
                }
                // 未授权 请发起授权信息
                log.info("测试用途:未授权 请发起授权信息");
                ctx.writeAndFlush(JSON.toJSONString(CloudR.Response(CloudDeviceConnRespEnum.CONNECT_ERROR_NO_AUTH)));

//                ctx.writeAndFlush("测试用途:未授权 请发起授权信息");
                return;
            } else {
                if(t == 3 ){
                    // 接收到设备的数据
                    DeviceDataDto deviceDataUpRo = JSON.parseObject(message, DeviceDataDto.class);
                    log.info("序列化后--->>>>{}", deviceDataUpRo);
                    // 保存数据 放到tdengine
                    int a = deviceService.saveDeviceData(dbr_SnByChannelId,deviceDataUpRo.getData());
                    if(a>0){
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
        String db_channelId_value = redisTemplate.opsForValue().get(CloudRedisKey.ChannelIdToDeviceSnKey + ctx.channel().id().asLongText());
        if(LmAssert.isNotEmpty(db_channelId_value)){

            String db_channelId_key = CloudRedisKey.ChannelIdToDeviceSnKey + ctx.channel().id().asLongText();
            String db_deviceSn_key = CloudRedisKey.DeviceSnToChannelIdKey + db_channelId_value;
            // 删除key
            redisTemplate.delete(db_channelId_key);
            redisTemplate.delete(db_deviceSn_key);
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
