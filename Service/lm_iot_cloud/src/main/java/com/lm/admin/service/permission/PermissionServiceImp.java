package com.lm.admin.service.permission;

import com.alibaba.fastjson2.JSON;
import com.lm.admin.entity.pojo.permission.Permission;
import com.lm.admin.mapper.mysql.permission.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lm
 * @date 2022/10/13 14:53
 */
@Service
@Slf4j
public class PermissionServiceImp implements PermissionService{

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 获取整个权限表的树 数据
     *
     * @return 权限表树
     */
    @Override
    public List<Permission> getPermissionTree() {

        // 先查询所有数据
        List<Permission> db_ret_allPermission = permissionMapper.findPermission();

        // 获取父节点
        List<Permission> rootMenu = db_ret_allPermission.stream().filter(
                root -> root.getPid().equals(0L)
        ).collect(Collectors.toList());
        // 通过父元素循环
        rootMenu.forEach(rootItem->PermissionsMenuTreeRecursive(rootItem,db_ret_allPermission));
        return rootMenu;
    }

    /**
     * 获取整个权限表中的菜单的树 数据
     *
     * @return
     */
    @Override
    public List<Permission> getMenuTree() {
        // 先查询所有数据权限表数据
        List<Permission> db_ret_allPermission = permissionMapper.findPermission();

        // 然后过滤出菜单数据
        List<Permission> db_ret_menu = db_ret_allPermission.stream().filter(item->item.getType() == 1).collect(Collectors.toList());


        // 获取菜单父节点
        List<Permission> rootMenu = db_ret_menu.stream().filter(
                root -> root.getPid().equals(0L)
        ).collect(Collectors.toList());
        // 通过菜单父元素循环
        rootMenu.forEach(rootItem->PermissionsMenuTreeRecursive(rootItem,db_ret_menu));

        return rootMenu;
    }

    /**
     * 获取整个权限表中的Api的树 数据
     *
     * @return
     */
    @Override
    public List<Permission> getApiTree() {
        // 先查询所有数据权限表数据
        List<Permission> db_ret_allPermission = permissionMapper.findPermission();

        // 然后过滤出菜单数据
        List<Permission> db_ret_menu = db_ret_allPermission.stream().filter(item->
                item.getType() == 2 || item.getPid()==0
        ).collect(Collectors.toList());


        // 获取菜单父节点
        List<Permission> rootMenu = db_ret_menu.stream().filter(
                root -> root.getPid().equals(0L)
        ).collect(Collectors.toList());
        // 通过菜单父元素循环
        rootMenu.forEach(rootItem->PermissionsMenuTreeRecursive(rootItem,db_ret_menu));

        return rootMenu;
    }

    /**
     * 权限表里面的菜单数据 遍历拼接层树
     * @param root
     * @param allList
     */
    private void PermissionsMenuTreeRecursive(Permission root ,List<Permission> allList){
        List<Permission> children_list =
                allList.stream().filter(
                    allItem -> allItem.getPid().equals(root.getId())
                ).collect(Collectors.toList());

        if (children_list == null){
            // 如果不赋值一个空数组的话，他可能会是一个null ，但debug看他也是一个空数据，算了加上吧
            children_list = new ArrayList<>();
            root.setChildren(children_list);
        }
        else{
            // 将子元素放到父类里面
            root.setChildren(children_list);
            // 将子元素再次遍历 无限子类的实现
            children_list.forEach(children_item->PermissionsMenuTreeRecursive(children_item,allList));
        }
    }
}
