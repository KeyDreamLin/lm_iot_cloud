package com.lm.cloud.common.r;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;

/**
 * 通用返回结构类，响应给客户端的 server->client
 * @param <T>
 */
@Getter
public class CloudR<T> implements java.io.Serializable  {
    // 对转json字符串后的排序 默认是abc顺序
    @JSONField(ordinal = 1)
    private Integer t;  // 请求响应状态
    @JSONField(ordinal = 2)
    private Integer status; // 响应状态
    @JSONField(ordinal = 3)
    private T data; // 一般用于命令请求

    private String cmdId;
    private CloudR() {
    }

    private CloudR(Integer t, Integer status) {
        this.t = t;
        this.status = status;
    }

    private CloudR(Integer t, Integer status, T data) {
        this.t = t;
        this.status = status;
        this.data = data;
    }
    // 用于发送cmd
    private CloudR(String cmdId ,T data) {
        this.cmdId = cmdId;
        this.data = data;
    }

    /**
     * 服务器响应客户端
     * @param cloudRespEnumInterface
     * @param <T>
     * @return
     */
    public static <T> CloudR<T> Response(CloudRespEnumInterface cloudRespEnumInterface) {
        return new CloudR<T>(
                cloudRespEnumInterface.getT(),
                cloudRespEnumInterface.getStatus());
    }

    /**
     * 服务端操作客户端 发送命令
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CloudR<T> Cmd (String cmdId ,T data) {
        return new CloudR<T>(cmdId,data);
    }

}
