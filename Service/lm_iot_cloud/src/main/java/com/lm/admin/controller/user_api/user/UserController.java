package com.lm.admin.controller.user_api.user;

import com.lm.admin.common.anno.apiauth.RoleAuth;
import com.lm.admin.controller.user_api.UserBaseController;
import com.lm.admin.entity.bo.user.UserRoleBo;
import com.lm.admin.entity.vo.user.UserRoleVo;
import com.lm.admin.service.user.UserServiceImpl;
import com.lm.admin.utils.pwd.DesUtils;
import com.lm.admin.utils.pwd.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理
 * path : /api/user/***
 * @author Lm
 * @date 2022/12/2 9:00
 */
@Slf4j
@RestController
public class UserController extends UserBaseController {
    @Autowired
    private UserServiceImpl userService;

    /**
     * 获取用户信息列表
     * api: /api/user/list
     * @param
     * @return user
     */
    @PostMapping("/list")
    @RoleAuth("CLOUD_ADMIN")
    public List<UserRoleBo> getUserList(){
        return userService.getUserRoleList();
    }

    /**
     * 添加一个用户信息
     * @param user
     * @return
     */
    @PostMapping("/add")
    @RoleAuth("CLOUD_ADMIN")
    public Integer addUser(@RequestBody UserRoleVo user){
        // 解密DES
        user.setPassword(DesUtils.decrypt(user.getPassword()));
        // 将加密后的md5传入到数据库
        user.setPassword(MD5Util.strToMd5s(user.getPassword()));
        return userService.addUser(user);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PostMapping("/upd")
    @RoleAuth("CLOUD_ADMIN")
    public Integer updUser(@RequestBody UserRoleVo user){
        // 解密DES
        user.setPassword(DesUtils.decrypt(user.getPassword()));
        // 将加密后的md5传入到数据库
        user.setPassword(MD5Util.strToMd5s(user.getPassword()));
        return userService.updateUser(user);
    }
}
