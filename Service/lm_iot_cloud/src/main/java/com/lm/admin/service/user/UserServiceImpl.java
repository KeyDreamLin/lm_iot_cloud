package com.lm.admin.service.user;


import com.lm.admin.entity.pojo.user.User;
import com.lm.admin.mapper.mysql.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户管理 服务实现类
 * @author Lm
 * @data 2022-11-21
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;


    /**
     * 根据用户账号获取用户信息
     * @param account 账号
     * @return User
     */
    @Override
    public User getUserByAccount(String account) {
        return userMapper.findUserByAccount(account);
    }

}
