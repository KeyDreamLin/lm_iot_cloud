package com.lm.admin.common.r;

/**
 * 服务器异常枚举
 */
public enum ServerErrorResultEnum implements GlobalResultEnumInterface{
    SERVER_ERROR(500,"服务器异常"),;

    private Integer code; //编码

    private String msg; //信息

    ServerErrorResultEnum(Integer code, String msg) {
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
