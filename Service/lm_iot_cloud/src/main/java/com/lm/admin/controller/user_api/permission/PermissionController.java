package com.lm.admin.controller.user_api.permission;

import com.lm.admin.controller.common_api.CommonBaseController;
import com.lm.admin.controller.user_api.UserBaseController;
import com.lm.admin.entity.pojo.permission.Permission;
import com.lm.admin.service.permission.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 权限接口 管理员和普通用户共用
 * path : /api/user/***
 * @author Lm
 * @date 2022/10/13 15:25
 */
@Slf4j
@RestController
public class PermissionController extends UserBaseController {

    @Autowired
    private PermissionService permissionServiceImp;

    /**
     * 获取菜单树接口
     * path : /api/user/menutree
     * @return
     */
    @ResponseBody
    @PostMapping("/menutree")
    public List<Permission> menuTree(){
        List<Permission> permissionTree = permissionServiceImp.getMenuTree();
        return permissionTree;
    }
}
