package com.lm.admin.mapper.mysql.roles;

import com.lm.admin.entity.pojo.roles.Roles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 角色管理mapper mysql
 * @author Lm
 * @date 2022/11/22 8:14
 */
@Mapper
public interface RolesMapper {
    /**
     * 根据用户id查询出对应的role信息
     * @param uid
     * @return
     */
    Roles findRolesByUserId(@Param("u_id") Long uid);

}
