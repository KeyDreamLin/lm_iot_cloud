package com.lm.admin.config.mybatis.annotation;

import lombok.Getter;


/**
 * 生成ID类型枚举类
 * @author lm
 * @date 2022-11-16
 */
@Getter
public enum IdType {
    /**
     * 默认不处理
     */
    NONE(0),
    /* 以下2种类型、只有当插入对象ID 为空，才自动填充。 */
    /**
     * 分配ID (主键类型为number或string）,
     * 默认实现类 {@link com.lm.admin.utils.SnowflakeIdUtils}(雪花算法)
     *
     * @since 3.3.0
     */
    ASSIGN_ID(1),
    /**
     * 分配UUID (主键类型为 string)
     * 默认实现类 {@link }(UUID.replace("-",""))
     */
    ASSIGN_UUID(2);

    private final int key;

    IdType(int key) {
        this.key = key;
    }
}
