package com.lm.admin.common.ex;

import lombok.Data;

/**
 * 统一异常管理bean类
 */
@Data
public class ErrorHandler {
    // ErrorHandler === CloudR 答案：不想破坏R类。
    // 异常的状态码，从枚举中获得
    private Integer status;
    // 异常的消息，写用户看得懂的异常，从枚举中得到
    private Object message;
    // 异常的名字
    private Object exception;

    private ErrorHandler() {
    }

//    public ErrorHandler(Integer status, Object message, String exception) {
//        this.status = status;
//        this.message = message;
//        this.exception = exception;
//    }
    // 这个直接传入异常的类 灵活性高一点
    public static ErrorHandler error(Integer status, Object message, Throwable e) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setStatus(status);
        errorHandler.setMessage(message);
        errorHandler.setException(e);
        return errorHandler;
    }
    // 可以直接传入异常的类名
    public static ErrorHandler error(Integer status, Object message, String e) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setStatus(status);
        errorHandler.setMessage(message);
        errorHandler.setException(e);
        return errorHandler;
    }

}
