package com.lm.admin.service.devicestrategy;

import com.lm.admin.common.ex.lthrow.ValidatorExceptionThrow;
import com.lm.admin.common.r.UserResultEnum;
import com.lm.admin.entity.bo.devicestrategy.DeviceStrategyInfoBo;
import com.lm.admin.entity.bo.devicestrategy.DeviceStrategyListBo;
import com.lm.admin.entity.dto.devicestrategy.DeviceStrategyDto;
import com.lm.admin.entity.dto.user.UserHeader;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategySaveVo;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategyUpdateVo;
import com.lm.admin.mapper.mysql.devicemodel.BaseDeviceModelMapper;
import com.lm.admin.mapper.mysql.devicestrategy.BaseDeviceStrategyMapper;
import com.lm.admin.mapper.mysql.devicestrategy.RoleAdminDeviceStrategyMapper;
import com.lm.admin.mapper.mysql.devicestrategy.RoleUserDeviceStrategyMapper;
import com.lm.admin.utils.lmthreadlocal.RoleThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 策略服务实现类
 * @author Lm
 * @date 2022/11/6 15:17
 */
@Service
@Slf4j
public class DeviceStrategyServiceImpl implements IDeviceStrategyService {
    @Autowired
    private RoleAdminDeviceStrategyMapper roleAdminDeviceStrategyMapper;

    @Autowired
    private RoleUserDeviceStrategyMapper roleUserDeviceStrategyMapper;

    private UserHeader userHeader;

    // 根据角色判断使用那个mapper 普通用户的会查询中间表
    private BaseDeviceStrategyMapper getBaseDeviceStrategyMapper(){
        userHeader = RoleThreadLocal.get();
        if(userHeader.getUserRoleCode()!=null && userHeader.getUserRoleCode().equals("CLOUD_USER")){
            return roleUserDeviceStrategyMapper;
        }
        else if(userHeader.getUserRoleCode()!=null && userHeader.getUserRoleCode().equals("CLOUD_ADMIN")){
            return roleAdminDeviceStrategyMapper;
        }
        // 如果不是管理员又不是普通用户的情况下 抛出 权限异常
        throw new ValidatorExceptionThrow(UserResultEnum.USER_ROLE_EXCEPTION);
    }

    /**
     * 这个给自动任务用的 所有直接赋予最高权限
     * 策略 查询列表
     * @return
     */
    @Override
    public List<DeviceStrategyDto> getAllDeviceStrategyDto() {
        return roleAdminDeviceStrategyMapper.findAllDeviceStrategyDto();
    }

    /**
     * 策略 查询列表
     * @return
     */
    @Override
    public List<DeviceStrategyListBo> getDeviceStrategyList() {
        return getBaseDeviceStrategyMapper().findDeviceStrategyList(userHeader.getUserId());
    }

    /**
     * 根据策略id查询策略信息
     * @param Sid
     * @return
     */
    @Override
    public DeviceStrategyInfoBo getDeviceStrategyById(Long Sid) {
        return getBaseDeviceStrategyMapper().findDeviceStrategyById(Sid);
    }

    /**
     * 更新策略的信息
     * @param deviceStrategyUpdateVo
     * @return
     */
    @Override
    public int updateDeviceStrategy(DeviceStrategyUpdateVo deviceStrategyUpdateVo) {
        if(deviceStrategyUpdateVo==null){
            return -1;
        }
        return getBaseDeviceStrategyMapper().updateDeviceStrategy(deviceStrategyUpdateVo);
    }

    /**
     * 平台设备策略总数
     *
     * @return
     */
    @Override
    public Integer getDeviceStrategyAllCount() {
        return getBaseDeviceStrategyMapper().findDeviceStrategyAllCount();
    }

    /**
     * 平台策略启用的数量
     *
     * @return
     */
    @Override
    public Integer getOpenDeviceStrategyCount() {
        return getBaseDeviceStrategyMapper().findOpenDeviceStrategyCount();
    }

    /**
     * 添加一条策略信息
     * @param deviceStrategySaveVo
     * @return
     */
    @Override
    public int addDeviceStrategy(DeviceStrategySaveVo deviceStrategySaveVo) {
        if(deviceStrategySaveVo==null){
            return -1;
        }
        return getBaseDeviceStrategyMapper().addDeviceStrategy(deviceStrategySaveVo);
    }


}
