package com.lm.admin.mapper.mysql.adminuser;

import com.lm.admin.entity.pojo.adminuser.AdminUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.sql.Date;

/**
 * 系统管理员 Mapper接口
 * @author Lm
 * @since 2022-10-02
 */
@Mapper
public interface AdminUserMapper {

    /**
    * @description: 根据用户账号查询用户信息
    * @param account
    * @return AdminUser
    * @author: Lm
    * @dae: 2022/10/2 15:3
    **/
    AdminUser findAdminUserByAccount(@Param("account") String account);
    // 添加Admin用户 根据id

    // 更新Admin用户 根据id

    // 保存Admin用户 根据id

    // 删除Admin用户 根据id
}
