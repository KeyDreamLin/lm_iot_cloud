package com.lm.admin.common.r;

/**
 * 异常处理异常接口统一约束
 */
public interface GlobalResultEnumInterface {

    Integer getCode();//编码
    String getMsg();//错误信息
}
