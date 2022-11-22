package com.lm.admin.service.roles;

import com.lm.admin.entity.pojo.roles.Roles;
import com.lm.admin.mapper.mysql.roles.RolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色管理服务 实现类
 * @author Lm
 * @date 2022/11/22 9:05
 */
@Service
public class RolesServiceImpl implements IRolesService{
    @Autowired
    private RolesMapper rolesMapper;

    /**
     * 根据用户id查询出对应的role信息
     *
     * @param uid
     * @return
     */
    @Override
    public Roles getRolesByUserId(Long uid) {
        return rolesMapper.findRolesByUserId(uid);
    }
}
