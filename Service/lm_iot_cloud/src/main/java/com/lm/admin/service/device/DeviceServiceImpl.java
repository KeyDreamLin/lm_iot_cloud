package com.lm.admin.service.device;

import com.lm.admin.entity.dto.device.DeviceDto;
import com.lm.admin.entity.pojo.device.Device;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
*  设备信息服务类
* @author Lm
* @since 2022-09-23
*/
public interface DeviceServiceImpl {
    /**
     * 根据sn码查询到设备信息
     * @param sn
     * @return
     */
    DeviceDto getDeviceBySn(String sn);


    /**
     * 保存设备数据
     * @param sn sn码
     * @param dataMap 设备数据集合
     * @return
     */
    int saveDeviceData(String sn, Map<String, String> dataMap);
}