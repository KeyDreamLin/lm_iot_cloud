package com.lm.admin.service.devicegrouping;


import com.lm.admin.common.ex.lthrow.ValidatorExceptionThrow;
import com.lm.admin.common.r.UserResultEnum;
import com.lm.admin.entity.dto.user.UserHeader;
import com.lm.admin.entity.pojo.devicegrouping.DeviceGrouping;
import com.lm.admin.entity.vo.devicegrouping.DeviceGroupingPageVo;
import com.lm.admin.entity.vo.devicegrouping.DeviceGroupingUpdateAndSaveVo;
import com.lm.admin.mapper.mysql.device.BaseDeviceMapper;
import com.lm.admin.mapper.mysql.devicegrouping.BaseDeviceGroupingMapper;
import com.lm.admin.mapper.mysql.devicegrouping.RoleAdminDeviceGroupingMapper;
import com.lm.admin.mapper.mysql.devicegrouping.RoleUserDeviceGroupingMapper;
import com.lm.admin.mapper.mysql.owner.OwnerMapper;
import com.lm.admin.utils.lmthreadlocal.RoleThreadLocal;
import com.lm.admin.utils.mybiats.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private OwnerMapper ownerMapper;

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

    /**
     * 添加一个设备 分组
     * @param deviceGrouping
     * @return
     */
    @Override
    @Transactional // 事务  多数据表操作记得开事务啊！！！！！！
    public Integer addDeviceGrouping(DeviceGroupingUpdateAndSaveVo deviceGrouping) {
        // 添加一个设备
        int retRow =  getBaseDeviceMapper().addDeviceGrouping(deviceGrouping);
        userHeader = RoleThreadLocal.get();
        // 管理员可以直接传用户id过来 然后添加分组
        // 普通用户请求头会自带的
        if(userHeader.getUserRoleCode()!=null && userHeader.getUserRoleCode().equals("CLOUD_USER")){
            // 添加设备分组 用户id 分组id   添加用户 拥有 分组表
            ownerMapper.addUserOwnerGrouping(userHeader.getUserId(), deviceGrouping.getId());;
        }
        else if(userHeader.getUserRoleCode()!=null && userHeader.getUserRoleCode().equals("CLOUD_ADMIN")){
            // 添加设备分组 用户id 分组id   添加用户 拥有 分组表
            ownerMapper.addUserOwnerGrouping(deviceGrouping.getUserId(), deviceGrouping.getId());
        }

        return retRow;
    }

    /**
     * 修改设备分组信息 并 修改分组拥有的设备
     *
     * @param deviceGrouping
     * @return
     */
    @Override
    @Transactional // 事务
    public Integer updDeviceGrouping(DeviceGroupingUpdateAndSaveVo deviceGrouping) {
        // 修改分组信息
        getBaseDeviceMapper().updDeviceGrouping(deviceGrouping);
        // 先删除分组拥有的设备
        ownerMapper.delGroupingByGid(deviceGrouping.getId());
        // 添加分组拥有的设备
        int row = ownerMapper.addGroupingOwnerDevices(deviceGrouping.getId(),deviceGrouping.getDeviceIds());
        return row;
    }

    /**
     * 删除一个设备分组
     * @param groupingId 分组id
     * @return
     */
    @Override
    @Transactional // 事务
    public Integer delDeviceGrouping(Long groupingId) {
        // 删除设备分组信息
        int row =  getBaseDeviceMapper().delDeviceGrouping(groupingId);
        // 删除设备分组拥有设备的中间表数据
        ownerMapper.delGroupingByGid(groupingId);
        // 删除用户拥有的设备分组
        ownerMapper.delUserOwnerGroupingByGid(groupingId);
        return row;
    }

    /**
     * 查询设备分组拥有的设备
     * @param groupingId 设备分组id
     * @return
     */
    @Override
    public List<Long> getGroupingOwnerDeviceById(Long groupingId) {
        return ownerMapper.findGroupingOwnerDeviceById(groupingId);
    }

    /**
     * 根据分组id查询到分组
     * @param groupingId 分组id
     * @return
     */
    @Override
    public DeviceGrouping getDeviceGroupingById(Long groupingId) {
        return getBaseDeviceMapper().findDeviceGroupingByGid(userHeader.getUserId(),groupingId);
    }

}
