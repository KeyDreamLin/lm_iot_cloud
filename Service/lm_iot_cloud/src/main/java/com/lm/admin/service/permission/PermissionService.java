package com.lm.admin.service.permission;

import com.lm.admin.entity.pojo.permission.Permission;

import java.util.List;

/**
 * @author Lm
 * @date 2022/10/13 14:52
 */
public interface PermissionService {

    /**
     * 获取整个权限表的树 数据
     * @return
     */
    List<Permission> getPermissionTree();
    /**
     * 获取整个权限表中的菜单的树 数据
     * @return
     */
    List<Permission> getMenuTree();
    /**
     * 获取整个权限表中的Api的树 数据
     * @return
     */
    List<Permission> getApiTree();
}
