package com.lm.admin.mapper.mysql.device;

import com.lm.admin.entity.dto.device.DeviceAuthDto;
import com.lm.admin.entity.pojo.device.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    DeviceAuthDto findDeviceBySn(@Param("sn") String sn);

    /**
     * 查询全部的设备数量
     * @return
     */
    Integer findDeviceCount();

    /**
     * 分页查询
     * @param pageIndex 当前页数
     * @param pageSize  当前页数有多少条
     * @return
     */
    List<Device> findDeicePage(
            @Param("pageIndex") Integer pageIndex ,
            @Param("pageSize") Integer pageSize,
            @Param("keyword") String keyword
    );
}
