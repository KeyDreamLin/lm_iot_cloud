package com.lm.admin.entity.pojo.adminuser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * 系统管理员
 * @author Lm
 * @since 2022-10-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUser implements Serializable {

    //主键
    private Long id;
    //名称、姓名
    private String username;
    //账号
    private String account;
    //密码
    private String password;
    // 头像
    private String avatar;
    // 发布状态 0 禁用 1未禁用
    private Integer status;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
