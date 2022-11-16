package com.lm.admin.config.mybatis.annotation;

import java.lang.annotation.*;


/**
 * 表字段标识
 * @author lm
 * @since 2022-11-16
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableField {

    /**
     * 字段自动填充策略
     * <p>
     * 在对应模式下将会忽略 insertStrategy 或 updateStrategy 的配置,等于断言该字段必有值
     */
    FieldFill fill() default FieldFill.DEFAULT;
}

