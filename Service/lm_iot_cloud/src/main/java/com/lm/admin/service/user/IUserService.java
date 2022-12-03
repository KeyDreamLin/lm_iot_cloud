package com.lm.admin.service.user;

import com.lm.admin.entity.bo.user.UserRoleBo;
import com.lm.admin.entity.pojo.user.User;
import com.lm.admin.entity.vo.user.UserRoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户管理服务 service接口
 * @author Lm
 * @data 2022-11-21
 */
public interface IUserService {


    /**
    * @description: 根据用户账号 获取用户信息
    * @author: lm
    * @date: 2022/11/21 15:24
    * @param account
    * @return com.lm.admin.entity.pojo.user.User
    **/
    User getUserByAccount(String account);

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    UserRoleBo getUserById(Long userId);

    /**
     * 获取用户信息和对应的角色信息 列表
     * @return
     */
    List<UserRoleBo> getUserRoleList();

    /**
     * 添加一个用户
     * @param user
     * @return
     */
    Integer addUser(UserRoleVo user);

    /**
     * 修改一个用户
     * @param user
     * @return
     */
    Integer updateUser(UserRoleVo user);
}
