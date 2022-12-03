package com.lm.admin.service.roles;

import com.lm.admin.entity.pojo.roles.Roles;

import java.util.List;

/**
 * 角色管理服务 service接口
 * @author Lm
 * @date 2022/11/22 9:05
 */
public interface IRolesService {
    /**
     * 根据用户id查询出对应的role信息
     * @param uid
     * @return
     */
    Roles getRolesByUserId(Long uid);

    /**
     * 查询所有的角色信息
     * @return
     */
    List<Roles> getRolesList();
}
