package com.lm.admin.common.r;

/**
 * 服务器成功处理枚举
 */
public enum ServerSuccessResultEnum implements GlobalResultEnumInterface{

    SERVER_SUCCESS(200,"success"),
    ;

    private Integer code; //编码

    private String msg; //信息

    ServerSuccessResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
