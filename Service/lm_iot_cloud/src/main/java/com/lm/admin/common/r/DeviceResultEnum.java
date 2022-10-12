package com.lm.admin.common.r;

/**
 * 可以理解为子模块的异常枚举
 * 设备异常、成功处理枚举
 */
public enum DeviceResultEnum implements GlobalResultEnumInterface{
    DEVICE_RESULT_CMD_SUCCEED(200100,"命令发送成功"),
    DEVICE_SN_NULL_ERROR(200101,"设备sn码不能为空"),
    DEVICE_DATA_NULL_ERROR(200101,"Data参数必须携带，值可以为空"),
    DEVICE_NOT_ONLINE_ERROR(200102,"设备离线状态，无法控制"),
    ;

    private Integer code; //编码

    private String msg; //错误信息

    DeviceResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
