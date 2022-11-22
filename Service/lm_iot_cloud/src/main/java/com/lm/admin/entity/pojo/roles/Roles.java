package com.lm.admin.entity.pojo.roles;

import com.lm.admin.config.mybatis.annotation.FieldFill;
import com.lm.admin.config.mybatis.annotation.IdType;
import com.lm.admin.config.mybatis.annotation.TableField;
import com.lm.admin.config.mybatis.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 角色信息 mysql
 * @author Lm
 * @date 2022/11/22 8:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Roles implements java.io.Serializable {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 角色代号
    private String roleCode;

    // 角色名称
    private String roleName;

    // 	发布状态 0 禁用 1未禁用
    private Integer	status;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
