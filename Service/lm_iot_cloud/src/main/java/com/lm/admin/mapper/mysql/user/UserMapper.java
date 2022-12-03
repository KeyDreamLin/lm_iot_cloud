package com.lm.admin.mapper.mysql.user;

import com.lm.admin.entity.bo.user.UserRoleBo;
import com.lm.admin.entity.pojo.user.User;
import com.lm.admin.entity.vo.user.UserRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.jta.UserTransactionAdapter;

import java.util.List;

/**
 * 用户管理 Mapper接口
 * @author Lm
 * @since 2022-1-21
 */
@Mapper
public interface UserMapper {
    /**
     * @description: 根据用户账号查询用户信息
     * @param account
     * @return AdminUser
     * @author: Lm
     * @dae: 2022/11/21 15:21
     **/
    User findUserByAccount(@Param("account") String account);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    UserRoleBo findUserById(@Param("userId") Long id);

    /**
     * 获取用户信息和对应的角色信息 列表
     * @return UserRoleBo
     */
    List<UserRoleBo> findUserAndRoleList();

    /**
     * 添加一个用户
     * @param user
     * @return
     */
    Integer addUser(@Param("u") UserRoleVo user);

    Integer updateUser(@Param("u") UserRoleVo user);

}