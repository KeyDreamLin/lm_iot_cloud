package com.lm.admin.entity.bo.user;

import com.lm.admin.entity.pojo.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 登录返回信息
 * @author Lm
 * @date 2022/11/21 15:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginBo implements Serializable {

    // 登录的用户信息
    private User user;

    // 角色代号
    private String roleCode;

    // 存储jwt 登录秘钥
    private String tokenJj;


}
