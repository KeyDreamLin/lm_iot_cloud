package com.lm.admin.service.adminuser;


import com.lm.admin.entity.dto.device.DeviceDto;
import com.lm.admin.entity.pojo.adminuser.AdminUser;
import com.lm.admin.mapper.mysql.adminuser.AdminUserMapper;
import com.lm.admin.mapper.mysql.device.DeviceMapper;
import com.lm.admin.mapper.tdengine.DeviceDataMapper;
import com.lm.admin.service.device.DeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 系统管理员 服务实现类
 * @author Lm
 * @data 2022-10-02
 */
@Service
public class AdminUserService implements AdminUserServiceImpl {
    // adminUser数据库接口
    @Autowired
    private AdminUserMapper adminUserMapper;


    /**
     * @description: 登录
     * @author: lm
     * @date: 2022/10/2 15:50
     * @param account
     * @return com.lm.admin.entity.pojo.adminuser.AdminUser
     **/
    @Override
    public AdminUser getAdminUserByAccout(String account) {
        return adminUserMapper.findAdminUserByAccount(account);
    }
}