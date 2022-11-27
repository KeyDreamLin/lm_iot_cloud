package com.lm.admin.entity.pojo.devicemodeltype;

import com.lm.admin.config.mybatis.annotation.FieldFill;
import com.lm.admin.config.mybatis.annotation.IdType;
import com.lm.admin.config.mybatis.annotation.TableField;
import com.lm.admin.config.mybatis.annotation.TableId;

import java.util.Date;

/**
 * 物模型类型表 mysql
 * @author Lm
 * @date 2022/11/25 16:53
 */
public class DeviceModelType implements java.io.Serializable {

    // 属性类型id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 属性类型
    private String dataType;

    // 属性类型名称
    private String dataName;

    // 性类型说明 json
    private String dataSpecs;

    // 属性描述
    private String describe;

    // 发布状态 0 禁用 1未禁用
    private int status;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
