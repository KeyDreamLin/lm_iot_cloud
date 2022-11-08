package com.lm.admin.mapper.mysql.device;

import com.lm.admin.entity.dto.devicestrategy.DeviceStrategyDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 设备策略 - mysql
 * @author Lm
 * @date 2022/11/6 15:02
 */
@Mapper
public interface DeviceStrategyMapper {
    /**
     * 查询所有策略 用于定时任务的
     * @return
     */
    List<DeviceStrategyDto> findAllDeviceStrategyDto();
}
