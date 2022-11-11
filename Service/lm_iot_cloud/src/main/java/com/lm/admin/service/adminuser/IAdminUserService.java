package com.lm.admin.service.adminuser;

import com.lm.admin.entity.pojo.adminuser.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统管理员 service接口
 * @author Lm
 * @data 2022-10-02
 */
public interface IAdminUserService {


    /**
    * @description: 登录
    * @author: lm
    * @date: 2022/10/2 15:50
    * @param account
    * @return com.lm.admin.entity.pojo.adminuser.AdminUser
    **/
    AdminUser getAdminUserByAccout(String account);
}
