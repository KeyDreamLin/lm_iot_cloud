package com.lm.admin.common.ex.lthrow;


import com.lm.admin.common.r.GlobalResultEnumInterface;

/**
 * 自定义异常类抛出
 */
public class ValidatorExceptionThrow extends RuntimeException{
    private Integer code;
    private String msg;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param globalResultEnumInterface the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ValidatorExceptionThrow(GlobalResultEnumInterface globalResultEnumInterface) {
        super(globalResultEnumInterface.getMsg());
        this.code = globalResultEnumInterface.getCode();
        this.msg = globalResultEnumInterface.getMsg();
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
