package com.lm.admin.service.device;

import com.lm.admin.entity.bo.device.DeviceBo;
import com.lm.admin.entity.bo.device.DeviceIdentifierAndNameDataBo;
import com.lm.admin.entity.bo.device.DeviceModelAndNewDataBo;
import com.lm.admin.entity.bo.device.DeviceSelectBo;
import com.lm.admin.entity.dto.device.DeviceAuthDto;
import com.lm.admin.entity.dto.device.DeviceNewDataDto;
import com.lm.admin.entity.vo.device.DevicePageVo;
import com.lm.admin.utils.mybiats.Pager;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
*  设备信息服务类
* @author Lm
* @since 2022-09-23
*/
public interface IDeviceService {
    /**
     * 根据sn码查询到设备信息 - 用于鉴权
     * @param sn
     * @return
     */
    DeviceAuthDto getDeviceBySn(String sn);
    /**
     * 根据sn码查询到设备信息
     * @param sn
     * @return
     */
    DeviceBo getDeviceBoBySn(String sn);

    /**
     * 查询全部设备
     * @return
     */
    List<DeviceBo> getDeviceList();

    /**
     * 根据设备分组id查询到对应的设备信息列表
     * @param gid 分组id
     * @return
     */
    List<DeviceBo> getDevicesByGroupingId(Long gid);

    /**
     * 返回设备sn和设备名称列表
     * @return
     */
    List<DeviceSelectBo> getDeviceSnName();



    Integer getDeviceCount();
}
