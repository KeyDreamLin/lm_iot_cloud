package com.lm.admin.mapper.mysql.devicemodel;

import com.lm.admin.entity.pojo.devicemodel.DeviceModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * 设备模型数据Mapper 父接口
 * @author Lm
 * @since 2022-11-24
 */
public interface BaseDeviceModelMapper {
    /**
     * 根据sn查询到指定设备
     * @param uid  管理员查询的时候可以随意赋值
     * @param sn
     * @return
     */
    List<DeviceModel> findDeviceModelBySn(@Param("uid") Long uid, @Param("sn") String sn);

    /**
     * 根据设备分组查询到对应的设备下的物模型数据
     * @param grouping_device_id
     * @return
     */
    List<DeviceModel> findDeviceGroupingModelByGid(@Param("grouping_device_id") Long grouping_device_id);

    /**
     * 查询有多少条物模型数据
     * @return
     */
    Integer findDeviceModelAllCount();

    /**
     * 查询今天新增了多少条物模型数据
     * @return
     */
    Integer findThisDayNewDeviceModelCount();

}
