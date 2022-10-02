package com.lm.cloud.common.r;

/**
 * 设备鉴权统一返回枚举类  CONN_RESP
 */
public enum CloudDeviceConnRespEnum implements CloudRespEnumInterface {
    CONNECT_SUCCESS(2 , 0),                 // 设备连接成功
    CONNECT_ERROR_NOT_ADD_DEVICE(2 , 1),    // 设备连接失败 未添加设备
    CONNECT_ERROR_AUTH(2,2),                // 设备连接失败 设备鉴权失败
    CONNECT_ERROR_UPS(2,3),                 // 设备连接失败 设备重复上线
    CONNECT_ERROR_NO_AUTH(2,4),                // 设备连接失败 设备鉴权失败
    ;
    private Integer t;// 请求响应状态
    private Integer status; // 响应状态

    CloudDeviceConnRespEnum(int t, int status) {
        this.t = t ;
        this.status = status ;
    }

    @Override
    public Integer getT() {
        return t;
    }

    @Override
    public Integer getStatus() {
        return status;
    }
}
