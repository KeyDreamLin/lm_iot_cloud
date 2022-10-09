package com.lm.cloud.common.auth;

import com.alibaba.fastjson2.JSON;
import com.lm.admin.entity.dto.device.DeviceDto;
import com.lm.admin.service.device.IDeviceService;
import com.lm.admin.tool.LmAssert;
import com.lm.admin.entity.dto.device.DeviceDataDto;
import com.lm.cloud.common.r.CloudDeviceConnRespEnum;
import com.lm.cloud.common.r.CloudR;
import com.lm.common.redis.devicekey.CloudRedisKey;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 云平台设备接入鉴权实现类
 */
@Slf4j
@Service
public class DeviceAuth {

    @Autowired
    private StringRedisTemplate redisTemplate;  // 操作Redis

    @Autowired
    private IDeviceService iDeviceService; //查询设备mysql

    public CloudR TcpAuth(ChannelHandlerContext ctx, String msg){

        DeviceDataDto deviceDataUpCo = JSON.parseObject(msg, DeviceDataDto.class);

        Integer tcp_ret_t = deviceDataUpCo.getT();
        String tcp_ret_deviceSn = deviceDataUpCo.getSn();
        String tcp_ret_secretKey = deviceDataUpCo.getSecretKey();
        if (tcp_ret_deviceSn==null){return null;}
        if (tcp_ret_secretKey==null){return null;}

        // 通过sn获取id 如果存在id就代表设备已经上线了
        String dbR_id = redisTemplate.opsForValue().get(CloudRedisKey.DeviceSnToChannelIdKey + tcp_ret_deviceSn);
        if(LmAssert.isNotEmpty(dbR_id)){
            log.info("一个设备只允许上线一次！");
//            ctx.writeAndFlush("测试用途:一个设备只允许上线一次");
//            return false;
            return CloudR.Response(CloudDeviceConnRespEnum.CONNECT_ERROR_UPS);

        }

        // 先鉴权设备 根据sn查询到key
        DeviceDto deviceBySn = iDeviceService.getDeviceBySn(tcp_ret_deviceSn);
        if(deviceBySn==null){
            // sn设备未添加
            log.info("sn设备未添加！");
//            ctx.writeAndFlush("测试用途:sn设备未添加");
            return CloudR.Response(CloudDeviceConnRespEnum.CONNECT_ERROR_NOT_ADD_DEVICE);
        }
        if(deviceBySn.getSn().equals(tcp_ret_deviceSn) &&
                deviceBySn.getSecretKey().equals(tcp_ret_secretKey)){
            log.info("鉴权到的设备sn--->{}",deviceBySn);
            // 双向绑定
            // channelId用于查询对应的sn码
            // sn码用于 一个sn只能上线一次
            redisTemplate.opsForValue().set(CloudRedisKey.DeviceSnToChannelIdKey + tcp_ret_deviceSn, ctx.channel().id().asLongText());
            redisTemplate.opsForValue().set(CloudRedisKey.ChannelIdToDeviceSnKey + ctx.channel().id().asLongText(), tcp_ret_deviceSn);
            // 设备在线计数器
            redisTemplate.opsForValue().increment(CloudRedisKey.DeviceOnLineCount, 1); // 设置递增因子
        }
        else{
            log.info("设备鉴权失败！");
//            ctx.writeAndFlush("测试用途:设备鉴权失败");
            return CloudR.Response(CloudDeviceConnRespEnum.CONNECT_ERROR_AUTH);
        }
        log.info("设备鉴权成功！");
//        ctx.writeAndFlush(CloudR.Response(CloudDeviceConnRespEnum.CONNECT_SUCCESS));
//        ctx.writeAndFlush("测试用途:设备鉴权成功");
        return CloudR.Response(CloudDeviceConnRespEnum.CONNECT_SUCCESS);
    }

    /**
     * sn加密后变成SecretKey 做传输秘钥
     * @param sn
     * @return
     */
    private static String SnToSecretKey(String sn){
        return "";
    }
}
