package com.lm.cloud.tcp.service.utils;

import com.lm.admin.utils.LmAssert;
import com.lm.common.redis.devicekey.CloudRedisKey;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 获取redis设备信息工具类
 * @author Lm
 * @date 2022/10/11 15:30
 */
@Component
public class RedisDeviceUtils {

    // 全局变量 用于存储sn对应的设备通道连接
    // TODO 后面看看能不能做集群 反正ChannelHandlerContext无法序列化存到redis
    public static Map<String, ChannelHandlerContext> deviceMap = new HashMap<String, ChannelHandlerContext>();

    // SpringBoot中自动注入无法注入静态对象，所以通过执行setStringRedisTemplate去给需要的静态对象赋值
    @Autowired
    private StringRedisTemplate stringRedisTemplate;  // 操作Redis

    private static StringRedisTemplate staticStringRedisTemplate;

    // 这个注解是java自带的注解（提供了规范，没有实现方法） 由spring实现 他的执行顺序是在Autowired之后  Autowired -> PostConstruct
    @PostConstruct
    private void setStringRedisTemplate(){
        staticStringRedisTemplate = stringRedisTemplate;
    }
    /**
     * 通过Cid作为key存Sn
     * @param sn
     * @param Cid
     * @return
     */
    public static Boolean setSnByCid(String sn,String Cid){
        staticStringRedisTemplate.opsForValue().set(CloudRedisKey.ChannelIdToDeviceSnKey + Cid, sn);
        return true;
        // 双向绑定
        // channelId用于查询对应的sn码
        // sn码用于 一个sn只能上线一次
//        SredisTemplate.opsForValue().set(CloudRedisKey.DeviceSnToChannelIdKey + tcp_ret_deviceSn, ctx.channel().id().asLongText());
//        SredisTemplate.opsForValue().set(CloudRedisKey.ChannelIdToDeviceSnKey + ctx.channel().id().asLongText(), tcp_ret_deviceSn);
    }
    /**
     * 通过Sn作为key存Cid
     * @param sn
     * @param Cid
     * @return
     */
    public static Boolean setCidBySn(String sn,String Cid){
        staticStringRedisTemplate.opsForValue().set(CloudRedisKey.DeviceSnToChannelIdKey + sn, Cid);
        return true;
    }


    /**
     * 根据通道id查询到设备的Sn码
     * @param Cid
     * @return
     */
    public static String getSnByCid(String Cid){
        return  staticStringRedisTemplate.opsForValue().get(CloudRedisKey.ChannelIdToDeviceSnKey + Cid );
    }
    /**
     * 根据设备的Sn码查询到通道id
     * @param sn
     * @return
     */
    public static String getCidBySn(String sn){
        return staticStringRedisTemplate.opsForValue().get(CloudRedisKey.DeviceSnToChannelIdKey + sn );
    }


    /**
     * 设备在线计数器
     * @param isOnLine 设备是否在线 true 加一个设备  false 减一个设备
     * @return
     */
    public static Long setDeviceOnLineCount(Boolean isOnLine){
        // 设备在线计数器
        return staticStringRedisTemplate.opsForValue().increment(CloudRedisKey.DeviceOnLineCount, (isOnLine?1:-1)); // 设置递增或者递减因子
    }


    /**
     * 获取设备在线数量
     * @return
     */
    public static Long getDeviceOnLineCount(){
        // 获取计数器的值 如果key不存在就是null
        Object count = staticStringRedisTemplate.opsForValue().get(CloudRedisKey.DeviceOnLineCount);
        return Long.valueOf((count == null ? 0 : count).toString());
    }

    /**
     * 通过设备sn码查询设备是否在线
     * @param sn
     * @return
     */
    public static Boolean getDeviceIsOnLienBySn(String sn){
        // 如果传入空字符串就返回false
        if(LmAssert.isEmpty(sn)){
            return false;
        }
        // 如果id不为空就返回true 代表设备在线的状态
        return  LmAssert.isNotEmpty(getCidBySn(sn));
    }

    /**
     * 删除全部设备在线状态
     * @return
     */
    public static Boolean delAll(){
        // 用于服务器异常、重启、维护、更新后id和sn还存在于redis中，影响设备的再次上线
        Set<String> keys = staticStringRedisTemplate.keys("lmCloud:*");//清空redis数据库中所有的键值对
        staticStringRedisTemplate.delete(keys);
        return true;
    }

}
