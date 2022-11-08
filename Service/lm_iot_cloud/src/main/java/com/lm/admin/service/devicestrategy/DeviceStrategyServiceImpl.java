package com.lm.admin.service.devicestrategy;

import com.lm.admin.entity.dto.devicestrategy.DeviceStrategyDto;

import java.util.List;

/**
 * 策略服务接口类
 * @author Lm
 * @date 2022/11/6 15:17
 */
public interface DeviceStrategyServiceImpl {
    /**
     * 获取所有的设备策略
     * @return
     */
    List<DeviceStrategyDto> getAllDeviceStrategyDto();

}
