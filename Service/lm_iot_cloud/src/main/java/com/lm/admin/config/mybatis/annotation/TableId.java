package com.lm.admin.config.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 表主键标识
 * @author lm
 * @since 2016-01-23
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableId {

    /**
     * 主键类型
     * {@link IdType}
     */
    IdType type() default IdType.NONE;

}
