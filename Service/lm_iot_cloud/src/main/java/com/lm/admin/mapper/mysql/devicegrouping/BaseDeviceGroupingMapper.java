package com.lm.admin.mapper.mysql.devicegrouping;

import com.lm.admin.entity.pojo.device.Device;
import com.lm.admin.entity.pojo.devicegrouping.DeviceGrouping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备分组 - mysql
 * @author Lm
 * @date 2022/10/31 15:42
 */
public interface BaseDeviceGroupingMapper {

    /**
     * 查询全部的设备分组数量
     * @return
     */
    Integer findDeviceGroupingCount();

    List<DeviceGrouping> findDeviceGroupingList(@Param("uid") Long uid);

}
