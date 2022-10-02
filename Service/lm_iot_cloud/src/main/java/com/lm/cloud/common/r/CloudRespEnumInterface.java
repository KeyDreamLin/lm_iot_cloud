package com.lm.cloud.common.r;

/**
 * 设备结果服务器到客户端接口 server->client
 */
public interface CloudRespEnumInterface {
    Integer getT();  // 请求响应状态
    Integer getStatus(); // 响应状态
}

