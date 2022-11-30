package com.lm.admin.mapper.mysql.device;

import com.lm.admin.entity.bo.device.DeviceSelectBo;
import com.lm.admin.entity.pojo.device.Device;
import com.lm.admin.entity.vo.device.DeviceSaveVo;
import com.lm.admin.entity.vo.device.DeviceUpdateVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备信息的一个父接口
 * @author Lm
 * @date 2022/11/22 20:32
 */
public interface BaseDeviceMapper {
    /**
     * 根据sn查询设备信息
     * @param sn
     * @return
     */
    Device findDeviceBySn(@Param("uid") Long uid, @Param("sn") String sn);

    /**
     * 查询全部的设备数量
     * @return
     */
    Integer findDeviceCount();

    /**
     * 查詢所有设备
     * @return
     */
    List<Device> findDeiceList(@Param("uid") Long uid);

    /**
     * 根据设备分组id查询到对应的设备信息列表
     * @param groupingId 分组id
     * @return
     */
    List<Device> findDevicesByGroupingId(@Param("groupingId") Long groupingId);

    List<DeviceSelectBo> findDeviceIdSnName(@Param("uid") Long uid);

    /**
     * 添加一个设备
     * @param deviceSaveVo
     * @return
     */
    int addDevice(@Param("ds") DeviceSaveVo deviceSaveVo);

    /**
     * 修改一个设备信息
     * @param deviceUpdateVo
     * @return
     */
    int updateDevice(@Param("du") DeviceUpdateVo deviceUpdateVo);

    /**
     * 根据设备id删除设备信息
     * @param id
     * @return
     */
    int delDeviceById(@Param("id") Long id);

}
