package com.lm.admin.mapper.mysql.user;

import com.lm.admin.entity.pojo.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    // 添加Admin用户 根据id

    // 更新Admin用户 根据id

    // 保存Admin用户 根据id

    // 删除Admin用户 根据id
}
