package com.lm.admin.entity.vo.user;

import com.lm.admin.config.mybatis.annotation.FieldFill;
import com.lm.admin.config.mybatis.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户登录 - mysql
 * @author Lm
 * @date 2022/11/21 15:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo implements java.io.Serializable {
    // 账号
    private String account;

    // 密码
    private String password;

    // 验证码
    private String code;

    // 验证码UUID
    private  String codeuuid;
}
