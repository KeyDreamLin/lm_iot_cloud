package com.lm.admin.common.r;

/**
 * 可以理解为子模块的异常枚举
 * 用户异常处理枚举
 */
public enum UserResultEnum implements GlobalResultEnumInterface{

    USER_INPUT_NULL_ERROR(100100,"用户输入有误！"),
    USER_NO_LOGIN(100101,"NO_LOGIN"),
    USER_LOGIN_NO_EXIST(100102,"登录失败，账号不存在/密码错误！"),
    USER_LOGIN_ACCOUNT_STATE_STOP_USE(100103,"登录失败，账户已被禁用！"),
    USER_TOKEN_ERROR(100104,"token异常"),
    USER_TOKEN_NOT_FOUND(100105,"token not found or due"),//令牌没找到或者过期，就是jwt过期了
    USER_NULL_ERROR(100106, "用户不存在"),
    USER_LOGIN_SAME(100107,"用户已在其他地方登录"),
    USER_INPUT_CODE_ERROR(100108,"验证码错误"),
    ID_NOT_EMPTY(100109, "id不允许为空"),
    ACCOUNT_REG_ERROR(100110,"该账号已存在！"),
    USER_ROLE_AUTH_EMPTY(100110,"权限不足！无法访问...")
    ;

    private Integer code; //编码

    private String msg; //错误信息

    UserResultEnum(Integer code, String msg) {
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
