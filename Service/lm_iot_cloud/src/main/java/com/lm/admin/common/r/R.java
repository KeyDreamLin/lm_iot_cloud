package com.lm.admin.common.r;

import lombok.Data;


/**
 * 通用返回结构类，服务端响应的数据最终都会封装成此对象
 * @param <T>
 */
@Data
public class R<T> {

    private Integer code; //编码

    private String msg; //错误信息

    private T data; //数据


    private R() {
    }

    private R(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> R<T> success(T object) {
        return new R<T>(ServerSuccessResultEnum.SERVER_SUCCESS.getCode(),
                        ServerSuccessResultEnum.SERVER_SUCCESS.getMsg(),
                        object);
    }

    public static <T> R<T> error(Integer code, T data, Object msg) {
        return new R<T>(code,msg.toString(),data);
    }

    public static <T> R<T> error(GlobalResultEnumInterface globalResultEnumInterface) {
        return new R<T>(globalResultEnumInterface.getCode(),globalResultEnumInterface.getMsg(),null);
    }
}
