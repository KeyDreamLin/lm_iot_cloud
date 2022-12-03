package com.lm.admin.mapper.mysql.devicemodel;

import com.lm.admin.entity.pojo.devicemodel.DeviceModel;
import com.lm.admin.entity.vo.devicemodel.DeviceModelUpdateSaveVo;
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
    Integer findDeviceModelAllCount(@Param("user_id") Long userId);

    /**
     * 查询今天新增了多少条物模型数据
     * @return
     */
    Integer findThisDayNewDeviceModelCount(@Param("user_id") Long userId);

    /**
     * 添加一条物模型数据
     * @param deviceModelUpdateSaveVo
     * @return
     */
    int addDeviceModel(@Param("dm") DeviceModelUpdateSaveVo deviceModelUpdateSaveVo);

    /**
     * 修改一条物模型数据
     * @param deviceModelUpdateSaveVo
     * @return
     */
    int updateDeviceModel(@Param("dm") DeviceModelUpdateSaveVo deviceModelUpdateSaveVo);


    /**
     * 根据id删除一条物模型数据
     * @param mid
     * @return
     */
    int delDeviceModelById(@Param("dmid")Long mid);

    /**
     * 根据设备id删除一条或者多条物模型数据
     * @param did
     * @return
     */
    int delDeviceModelByDeviceId(@Param("did")Long did);


}
