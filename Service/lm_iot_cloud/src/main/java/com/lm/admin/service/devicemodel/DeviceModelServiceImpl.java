package com.lm.admin.service.devicemodel;


import com.lm.admin.common.ex.lthrow.ValidatorExceptionThrow;
import com.lm.admin.common.r.UserResultEnum;
import com.lm.admin.entity.bo.device.DeviceModelAndNewDataBo;
import com.lm.admin.entity.bo.devicemodel.DeviceModelSelectBo;
import com.lm.admin.entity.dto.user.UserHeader;
import com.lm.admin.entity.vo.devicemodel.DeviceModelUpdateSaveVo;
import com.lm.admin.mapper.mysql.device.BaseDeviceMapper;
import com.lm.admin.mapper.mysql.devicemodel.BaseDeviceModelMapper;
import com.lm.admin.mapper.mysql.devicemodel.RoleAdminDeviceModelMapper;
import com.lm.admin.mapper.mysql.devicemodel.RoleUserDeviceModelMapper;
import com.lm.admin.utils.lmthreadlocal.RoleThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lm
 * @date 2022/10/8 16:48
 */
@Service
@Slf4j
public class DeviceModelServiceImpl implements IDeviceModelService {


    @Autowired
    private RoleAdminDeviceModelMapper roleAdminDeviceModelMapper;

    @Autowired
    private RoleUserDeviceModelMapper roleUserDeviceModelMapper;

    private UserHeader userHeader;
    // 根据角色判断使用那个mapper 普通用户的会查询中间表
    private BaseDeviceModelMapper getBaseDeviceModelMapper(){
        userHeader = RoleThreadLocal.get();
        if(userHeader.getUserRoleCode()!=null && userHeader.getUserRoleCode().equals("CLOUD_USER")){
            return roleUserDeviceModelMapper;
        }
        else if(userHeader.getUserRoleCode()!=null && userHeader.getUserRoleCode().equals("CLOUD_ADMIN")){
            return roleAdminDeviceModelMapper;
        }
        // 如果不是管理员又不是普通用户的情况下 抛出 权限异常
        throw new ValidatorExceptionThrow(UserResultEnum.USER_ROLE_EXCEPTION);
    }


    /**
     * 获取设备物模型列表
     * @param sn 设备sn码
     * @return List<DeviceModel>
     */
    @Override
    public List<DeviceModelAndNewDataBo> getDeiceModelBySn(String sn) {
        List<DeviceModelAndNewDataBo> listBo = new ArrayList<>();
        getBaseDeviceModelMapper().findDeviceModelBySn(userHeader.getUserId(), sn).forEach(item->{
            DeviceModelAndNewDataBo deviceModelAndNewDataBo = new DeviceModelAndNewDataBo();
            BeanUtils.copyProperties(item, deviceModelAndNewDataBo);
            listBo.add(deviceModelAndNewDataBo);
        });
        return listBo;
    }

    /**
     * 给策略下拉框中的标识符传递数据
     * @param sn
     * @return
     */
    @Override
    public List<DeviceModelSelectBo> getDeviceModelSelectBySn(String sn) {
        List<DeviceModelSelectBo> listBo = new ArrayList<>();
        getBaseDeviceModelMapper().findDeviceModelBySn(userHeader.getUserId(), sn).forEach(item->{
            DeviceModelSelectBo deviceModelSelectBo = new DeviceModelSelectBo();
            BeanUtils.copyProperties(item, deviceModelSelectBo);
            listBo.add(deviceModelSelectBo);
        });
        return listBo;
    }

    /**
     * 查询有多少条物模型数据
     *
     * @return
     */
    @Override
    public Integer getDeviceModelAllCount() {
        return getBaseDeviceModelMapper().findDeviceModelAllCount();
    }

    /**
     * 查询今天新增了多少条物模型数据
     *
     * @return
     */
    @Override
    public Integer getThisDayNewDeviceModelCount() {
        return getBaseDeviceModelMapper().findThisDayNewDeviceModelCount();
    }


    /**
     * 添加一条物模型数据
     * @param deviceModelUpdateSaveVo
     * @return
     */
    @Override
    public int addDeviceModel(DeviceModelUpdateSaveVo deviceModelUpdateSaveVo) {
        return getBaseDeviceModelMapper().addDeviceModel(deviceModelUpdateSaveVo);
    }

    /**
     * 修改一条物模型数据
     * @param deviceModelUpdateSaveVo
     * @return
     */
    @Override
    public int updateDeviceModel(DeviceModelUpdateSaveVo deviceModelUpdateSaveVo) {
        return getBaseDeviceModelMapper().updateDeviceModel(deviceModelUpdateSaveVo);
    }

    /**
     * 根据id删除一条物模型数据
     * @param mid
     * @return
     */
    @Override
    public int delDeviceModelById(Long mid) {
        return getBaseDeviceModelMapper().delDeviceModelById(mid);
    }

    /**
     * 根据设备id删除一条或者多条物模型数据  不提供给http
     * @param did 设备id
     * @return
     */
    @Override
    public int delDeviceModelByDeviceId(Long did) {
        return getBaseDeviceModelMapper().delDeviceModelByDeviceId(did);
    }


}
