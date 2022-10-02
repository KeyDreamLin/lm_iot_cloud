package com.lm.cloud.common.r;

/**
 * 上报数据确认统一枚举类 PUSH_ACK
 */
public enum CloudDevicePushAckEnum implements CloudRespEnumInterface {
    DATA_PUSH_ACK_SUCCESS(4 , 0),       // 数据上报成功
    DATA_PUSH_ACK_ERROR(4 , 1),         // 数据上报失败
    ;
    private Integer t;// 请求响应状态
    private Integer status; // 响应状态

    CloudDevicePushAckEnum(int t, int status) {
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
