package com.lm.admin.service.devicemodel;

import com.lm.admin.entity.pojo.devicemodel.DeviceModel;

import java.util.List;

/**
 * 设备物模型接口
 * @author Lm
 * @date 2022/10/8 16:48
 */

public interface DeviceModelService {

    /**
     * 获取设备物模型列表
     * @param sn 设备sn码
     * @return List<DeviceModel>
     */
    List<DeviceModel> getDeiceModelBySn(String sn);
}
