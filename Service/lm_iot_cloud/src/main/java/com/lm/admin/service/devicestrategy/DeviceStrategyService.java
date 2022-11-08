package com.lm.admin.service.devicestrategy;

import com.lm.admin.entity.dto.devicestrategy.DeviceStrategyDto;
import com.lm.admin.mapper.mysql.device.DeviceStrategyMapper;
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
public class DeviceStrategyService implements DeviceStrategyServiceImpl{
    @Autowired
    private DeviceStrategyMapper deviceStrategyMapper;

    @Override
    public List<DeviceStrategyDto> getAllDeviceStrategyDto() {
        return deviceStrategyMapper.findAllDeviceStrategyDto();
    }
}
