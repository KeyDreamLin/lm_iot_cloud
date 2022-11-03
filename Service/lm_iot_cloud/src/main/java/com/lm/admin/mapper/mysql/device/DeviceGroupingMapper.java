package com.lm.admin.mapper.mysql.device;

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
@Mapper
public interface DeviceGroupingMapper {

    /**
     * 查询全部的设备分组数量
     * @return
     */
    Integer findDeviceGroupingCount();
    /**
     * 分页查询
     * @param pageIndex 当前页数
     * @param pageSize  当前页数有多少条
     * @return
     */
    List<DeviceGrouping> findDeviceGroupingPage(
            @Param("pageIndex") Integer pageIndex ,
            @Param("pageSize") Integer pageSize,
            @Param("keyword") String keyword
    );


}
