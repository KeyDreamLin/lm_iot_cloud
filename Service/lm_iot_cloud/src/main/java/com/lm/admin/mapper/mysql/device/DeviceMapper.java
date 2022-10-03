package com.lm.admin.mapper.mysql.device;

import com.lm.admin.entity.dto.device.DeviceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 设备信息Mapper
 *
 * @author Lm
 * @since 2022-09-23
 */
@Mapper
public interface DeviceMapper {
    /**
     * 根据sn查询设备信息
     * @param sn
     * @return
     */
    DeviceDto findDeviceBySn(@Param("sn") String sn);

}
