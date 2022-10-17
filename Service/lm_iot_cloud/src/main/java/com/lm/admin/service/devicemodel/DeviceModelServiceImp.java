package com.lm.admin.service.devicemodel;


import com.lm.admin.entity.bo.devicemodel.DeviceModelBo;
import com.lm.admin.entity.pojo.devicemodel.DeviceModel;
import com.lm.admin.mapper.mysql.device.DeviceModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lm
 * @date 2022/10/8 16:48
 */
@Service
@Slf4j
public class DeviceModelServiceImp implements DeviceModelService {
    @Autowired
    private DeviceModelMapper deviceModelDataMapper;

    /**
     * 获取设备物模型列表
     * @param sn 设备sn码
     * @return List<DeviceModel>
     */
    @Override
    public List<DeviceModelBo> getDeiceModelBySn(String sn) {
        List<DeviceModelBo> listBo = new ArrayList<>();
        deviceModelDataMapper.findDeviceModelBySn(sn).forEach(item->{
            DeviceModelBo deviceModelBo = new DeviceModelBo();
            BeanUtils.copyProperties(item,deviceModelBo);
            listBo.add(deviceModelBo);
        });

        return listBo;
    }
}
