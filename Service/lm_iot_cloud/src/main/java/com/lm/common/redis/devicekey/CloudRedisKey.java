package com.lm.common.redis.devicekey;

public class CloudRedisKey {

    // 双向绑定起来 id查询sn码 鉴权用
    //  sn码查询id是否存在连接的设备 避免两个设备连接
    // 通过 channelId 保存设备的sn码
    public static String ChannelIdToDeviceSnKey = "lmCloud:cloud:auth:tcp:channelId:";
    // 通过 设备的sn码 保存channelId
    public static String DeviceSnToChannelIdKey = "lmCloud:cloud:auth:tcp:deviceSn:";
    // 所有设备在线计数器
    public static String DeviceOnLineCount = "lmCloud:cloud:count";
    // 设备最新数据 下一级放deviceSn进去
    public static String DeviceNewDataKey = "lmCloud:cloud:DeviceData:";
    // 设备策略任务池 下一级策略sn_identifier  val val:
    public static String DeviceStrategyTaskPool = "lmCloud:cloud:StrategyTaskPool:";
}
