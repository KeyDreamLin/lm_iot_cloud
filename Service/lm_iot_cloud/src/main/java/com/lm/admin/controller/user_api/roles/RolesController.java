package com.lm.admin.controller.user_api.roles;

import com.lm.admin.controller.user_api.UserBaseController;
import com.lm.admin.entity.pojo.roles.Roles;
import com.lm.admin.entity.pojo.user.User;
import com.lm.admin.service.roles.RolesServiceImpl;
import com.lm.admin.service.user.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理
 * path : /api/user/***
 * @author Lm
 * @date 2022/12/3 9:09
 */
@Slf4j
@RestController
public class RolesController extends UserBaseController {
    @Autowired
    private RolesServiceImpl rolesService;

    /**
     * 获取角色列表
     * api:/api/user/role/list
     * @return
     */
    @PostMapping("/role/list")
    public List<Roles> getRolesList(){
        return rolesService.getRolesList();
    }
}
