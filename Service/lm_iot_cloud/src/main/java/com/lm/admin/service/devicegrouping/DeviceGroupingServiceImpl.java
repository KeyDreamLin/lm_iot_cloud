package com.lm.admin.service.devicegrouping;


import com.lm.admin.common.ex.lthrow.ValidatorExceptionThrow;
import com.lm.admin.common.r.UserResultEnum;
import com.lm.admin.entity.dto.user.UserHeader;
import com.lm.admin.entity.pojo.devicegrouping.DeviceGrouping;
import com.lm.admin.entity.vo.devicegrouping.DeviceGroupingPageVo;
import com.lm.admin.mapper.mysql.device.BaseDeviceMapper;
import com.lm.admin.mapper.mysql.devicegrouping.BaseDeviceGroupingMapper;
import com.lm.admin.mapper.mysql.devicegrouping.RoleAdminDeviceGroupingMapper;
import com.lm.admin.mapper.mysql.devicegrouping.RoleUserDeviceGroupingMapper;
import com.lm.admin.utils.lmthreadlocal.RoleThreadLocal;
import com.lm.admin.utils.mybiats.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  服务实现类
*
* @author Lm
* @since 2022-09-23
*/
@Service
@Slf4j
public class DeviceGroupingServiceImpl implements IDeviceGroupingService {
    @Autowired
    private RoleAdminDeviceGroupingMapper roleAdminDeviceGroupingMapper;

    @Autowired
    private RoleUserDeviceGroupingMapper roleUserDeviceGroupingMapper;

    private UserHeader userHeader;

    // 根据角色判断使用那个mapper 普通用户的会查询中间表
    private BaseDeviceGroupingMapper getBaseDeviceMapper(){
        userHeader = RoleThreadLocal.get();
        if(userHeader.getUserRoleCode()!=null && userHeader.getUserRoleCode().equals("CLOUD_USER")){
            return roleUserDeviceGroupingMapper;
        }
        else if(userHeader.getUserRoleCode()!=null && userHeader.getUserRoleCode().equals("CLOUD_ADMIN")){
            return roleAdminDeviceGroupingMapper;
        }
        // 如果不是管理员又不是普通用户的情况下 抛出 权限异常
        throw new ValidatorExceptionThrow(UserResultEnum.USER_ROLE_EXCEPTION);
    }

    /**
     * 查询全部分组列表
     *
     * @return
     */
    @Override
    public List<DeviceGrouping> getDeviceGroupingList() {
        return getBaseDeviceMapper().findDeviceGroupingList(userHeader.getUserId());
    }

}
