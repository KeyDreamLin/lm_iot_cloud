package com.lm.admin.common.anno.apiauth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于对api的权限控制注解 传入权限的code
 */
@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)// 你可以通过反射获取到注解消息
public @interface RoleAuth {
    // 必填参数
    String value();
}
