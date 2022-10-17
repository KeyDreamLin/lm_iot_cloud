package com.lm.admin.mapper.mysql.device;

import com.lm.admin.entity.pojo.devicemodel.DeviceModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 设备模型数据Mapper
 * @author Lm
 * @since 2022-10-02
 */
@Mapper
public interface DeviceModelMapper {
    /**
     * 根据sn查询到指定设备
     * @param sn
     * @return
     */
    List<DeviceModel> findDeviceModelBySn(@Param("sn") String sn);

    /**
     * 根据设备分组查询到对应的设备下的物模型数据
     * @param grouping_device_id
     * @return
     */
    List<DeviceModel> findDeviceGroupingModelByGid(@Param("grouping_device_id") Long grouping_device_id);
}
