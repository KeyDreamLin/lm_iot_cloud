package com.lm.admin.service.user;


import com.lm.admin.entity.bo.user.UserRoleBo;
import com.lm.admin.entity.pojo.user.User;
import com.lm.admin.entity.vo.user.UserRoleVo;
import com.lm.admin.mapper.mysql.owner.OwnerMapper;
import com.lm.admin.mapper.mysql.user.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理 服务实现类
 * @author Lm
 * @data 2022-11-21
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OwnerMapper ownerMapper;

    /**
     * 根据用户账号获取用户信息
     * @param account 账号
     * @return User
     */
    @Override
    public User getUserByAccount(String account) {
        return userMapper.findUserByAccount(account);
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserRoleBo getUserById(Long userId) {
        return userMapper.findUserById(userId);
    }

    /**
     * 获取用户信息和对应的角色信息 列表
     * @return
     */
    @Override
    public List<UserRoleBo> getUserRoleList() {
        return userMapper.findUserAndRoleList();
    }

    /**
     * 添加一个用户
     * @param user
     * @return
     */
    @Override
    @Transactional // 事务
    public Integer addUser(UserRoleVo user) {
        int row = userMapper.addUser(user);
        ownerMapper.addUserOwnerRole(user.getId(), user.getRoleId());
        return row;
    }

    /**
     * 修改一个用户
     * @param user
     * @return
     */
    @Override
    @Transactional // 事务
    public Integer updateUser(UserRoleVo user) {
        int row = userMapper.updateUser(user);
        // 先删除 用户拥有的角色
        ownerMapper.delUserOwnerRoleByUserId(user.getId());
        // 再添加 用户拥有的角色
        ownerMapper.addUserOwnerRole(user.getId(), user.getRoleId());
        return row;
    }


}
