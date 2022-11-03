package com.lm.cloud.common.r;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 通用返回结构类，响应给客户端的 server->client
 * @param <T>
 */
@Getter
@AllArgsConstructor
public class CloudR<T> implements java.io.Serializable  {
    // 对转json字符串后的排序 默认是abc顺序
    @JSONField(ordinal = 1)
    private Integer t;  // 请求响应状态
    @JSONField(ordinal = 2)
    private Integer status; // 响应状态
    @JSONField(ordinal = 3)
    private String cmdId; // 发送命令id 自动生成
    @JSONField(ordinal = 4)
    private String apitag; // 传感器标识符
    @JSONField(ordinal = 4)
    private T data; // 一般用于命令请求



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
    private CloudR(Integer t, String cmdId ,String apitag,T data) {
        this.t = t;
        this.cmdId = cmdId;
        this.apitag = apitag;
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
    public static <T> CloudR<T> Cmd (String cmdId,String apitag ,T data) {
        return new CloudR<T> (5, cmdId,apitag, data);
    }

}
