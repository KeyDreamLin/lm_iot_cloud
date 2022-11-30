package com.lm.admin.entity.pojo.user;

import com.lm.admin.config.mybatis.annotation.FieldFill;
import com.lm.admin.config.mybatis.annotation.IdType;
import com.lm.admin.config.mybatis.annotation.TableField;
import com.lm.admin.config.mybatis.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户信息 - mysql
 * @author Lm
 * @date 2022/11/21 15:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements java.io.Serializable {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 名称、姓名
    private String username;

    // 账号
    private String account;

    // 头像url
    private String avatar;

    // 密码
    private String password;

    // 发布状态 0 禁用 1未禁用
    private Integer	status;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private void setT(){

    }
}
