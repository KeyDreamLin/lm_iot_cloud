package com.lm.admin.service.devicedata;

import com.lm.admin.entity.bo.device.DeviceBo;
import com.lm.admin.entity.bo.device.DeviceIdentifierAndNameDataBo;
import com.lm.admin.entity.bo.device.DeviceModelAndNewDataBo;
import com.lm.admin.entity.bo.device.DeviceSelectBo;
import com.lm.admin.entity.dto.device.DeviceAuthDto;
import com.lm.admin.entity.dto.device.DeviceNewDataDto;
import com.lm.admin.entity.vo.device.DevicePageVo;
import com.lm.admin.utils.mybiats.Pager;

import java.util.List;
import java.util.Map;

/**
*  设备信息服务类
* @author Lm
* @since 2022-09-23
*/
public interface IDeviceDataService {



    /**
     * 保存设备数据
     * @param sn sn码
     * @param dataMap 设备数据集合
     * @return
     */
    int saveDeviceData(String sn, Map<String, String> dataMap);

    /**
     * 保存设备最新数据使用redis
     *
     * @param deviceNewDataDto
     * @return
     */
    boolean saveDeviceDataRedis(DeviceNewDataDto deviceNewDataDto);

    /**
     * @description: 获取设备最新数据 redis
     * @param  sn, identifierMap
     * @return DeviceIdentifierAndNameDataBo  标识符对应的设备数据
     **/
    List<DeviceModelAndNewDataBo> getDeviceNewDataRedis(String sn) ;

    // 获取设备最新数据 td
    List<DeviceIdentifierAndNameDataBo> getDeviceNewData(String sn);






    /**
     * 根据设备sn码查询到设备上报数 如果sn为null就查询全部的数据
     * @param sn
     * @return
     */
    Long getDeviceDataUpCount(String sn);

    /**
     * 根据设备sn码查询到当天设备上报数 如果sn为null就查询全部的数据
     * @param sn
     * @return
     */
    Long getThisDayDeviceDataUpCount(String sn);


}
