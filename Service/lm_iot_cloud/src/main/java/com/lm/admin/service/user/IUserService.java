package com.lm.admin.service.user;

import com.lm.admin.entity.pojo.user.User;

/**
 * 用户管理服务 service接口
 * @author Lm
 * @data 2022-11-21
 */
public interface IUserService {


    /**
    * @description: 获取用户信息
    * @author: lm
    * @date: 2022/11/21 15:24
    * @param account
    * @return com.lm.admin.entity.pojo.user.User
    **/
    User getUserByAccount(String account);
}
