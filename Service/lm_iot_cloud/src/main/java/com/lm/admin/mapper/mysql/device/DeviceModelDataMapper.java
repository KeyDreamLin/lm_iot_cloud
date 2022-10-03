package com.lm.admin.mapper.mysql.device;

import com.lm.admin.entity.pojo.devicemodel.DeviceModelData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 设备模型数据Mapper
 * @author Lm
 * @since 2022-10-02
 */
@Mapper
public interface DeviceModelDataMapper {
    List<DeviceModelData> findDeviceModelBySn(@Param("sn") String sn);
}
