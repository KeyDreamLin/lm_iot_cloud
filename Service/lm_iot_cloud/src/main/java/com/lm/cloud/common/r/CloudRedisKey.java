package com.lm.cloud.common.r;

public class CloudRedisKey {

    // 双向绑定起来 id查询sn码 鉴权用
    //  sn码查询id是否存在连接的设备 避免两个设备连接
    // 通过 channelId 保存设备的sn码
    public static String ChannelIdToDeviceSnKey = "lmCloud:cloud:tcp:channelId:";
    // 通过 设备的sn码 保存channelId
    public static String DeviceSnToChannelIdKey = "lmCloud:cloud:tcp:deviceSn:";


}
