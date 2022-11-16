package com.lm.admin.config.mybatis.annotation;

/**
 * 字段填充策略枚举类
 * 判断注入的 insert 和 update 的 sql 脚本是否在对应情况下忽略掉字段的 if 标签生成
 * @author lm
 * @date 2022-11-16
 */
public enum FieldFill {
    /**
     * 默认不处理
     */
    DEFAULT,
    /**
     * 插入时填充字段
     */
    INSERT,
    /**
     * 更新时填充字段
     */
    UPDATE,
    /**
     * 插入和更新时填充字段
     */
    INSERT_UPDATE
}
