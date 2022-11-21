package com.lm.admin.service.devicemodel;


import com.lm.admin.entity.bo.device.DeviceModelAndNewDataBo;
import com.lm.admin.entity.bo.devicemodel.DeviceModelSelectBo;
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
public class DeviceModelServiceImpl implements IDeviceModelService {
    @Autowired
    private DeviceModelMapper deviceModelDataMapper;

    /**
     * 获取设备物模型列表
     * @param sn 设备sn码
     * @return List<DeviceModel>
     */
    @Override
    public List<DeviceModelAndNewDataBo> getDeiceModelBySn(String sn) {
        List<DeviceModelAndNewDataBo> listBo = new ArrayList<>();
        deviceModelDataMapper.findDeviceModelBySn(sn).forEach(item->{
            DeviceModelAndNewDataBo deviceModelAndNewDataBo = new DeviceModelAndNewDataBo();
            BeanUtils.copyProperties(item, deviceModelAndNewDataBo);
            listBo.add(deviceModelAndNewDataBo);
        });
        return listBo;
    }

    /**
     * 给策略下拉框中的标识符传递数据
     *
     * @param sn
     * @return
     */
    @Override
    public List<DeviceModelSelectBo> getDeviceModelSelectBySn(String sn) {
        List<DeviceModelSelectBo> listBo = new ArrayList<>();
        deviceModelDataMapper.findDeviceModelBySn(sn).forEach(item->{
            DeviceModelSelectBo deviceModelSelectBo = new DeviceModelSelectBo();
            BeanUtils.copyProperties(item, deviceModelSelectBo);
            listBo.add(deviceModelSelectBo);
        });
        return listBo;
    }

    /**
     * 查询有多少条物模型数据
     *
     * @return
     */
    @Override
    public Integer getDeviceModelAllCount() {
        return deviceModelDataMapper.findDeviceModelAllCount();
    }

    /**
     * 查询今天新增了多少条物模型数据
     *
     * @return
     */
    @Override
    public Integer getThisDayNewDeviceModelCount() {
        return deviceModelDataMapper.findThisDayNewDeviceModelCount();
    }


}
