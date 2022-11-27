package com.lm.admin.controller.device_api.devicemodel;

import com.lm.admin.controller.device_api.DeviceBaseController;
import com.lm.admin.entity.bo.device.DeviceModelAndNewDataBo;
import com.lm.admin.entity.bo.devicemodel.DeviceModelSelectBo;
import com.lm.admin.entity.vo.devicemodel.DeviceModelUpdateSaveVo;
import com.lm.admin.entity.vo.devicemodel.DeviceModelVo;
import com.lm.admin.service.devicemodel.IDeviceModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 设备物模型接口
 * api:/api/device/***
 * @author Lm
 * @date 2022/11/11 20:41
 */
@Slf4j
@RestController
public class DeviceModelController extends DeviceBaseController {
    @Autowired
    private IDeviceModelService deviceModelService;

    /**
     * 根据设备Sn查询到对应的物模型数据
     * path: /api/device/devicemodel
     * @param deviceModelVo
     * @return
     */
    @PostMapping("/devicemodel")
    public List<DeviceModelAndNewDataBo> getDeviceModelBySn(@RequestBody DeviceModelVo deviceModelVo){
        return deviceModelService.getDeiceModelBySn(deviceModelVo.getSn());
    }

    /**
     * 根据设备sn查询到物模型的数据名称和标识符
     * path: /api/device/devicemodel/select
     * @param deviceModelVo
     * @return
     */
    @PostMapping("/devicemodel/select")
    public List<DeviceModelSelectBo> getDeviceModelSelectData(@RequestBody DeviceModelVo deviceModelVo){
        return deviceModelService.getDeviceModelSelectBySn(deviceModelVo.getSn());
    }

    @PostMapping("/devicemodel/allcount")
    public Integer getDeviceModelAllCount(){
        return deviceModelService.getDeviceModelAllCount();
    }

    @PostMapping("/devicemodel/thisdaycount")
    public Integer getThisDayNewDeviceModelCount(){
        return deviceModelService.getThisDayNewDeviceModelCount();
    }

    /**
     * 添加一条物模型数据
     * path: /api/device/devicemodel/add
     * @param deviceModelUpdateSaveVo
     * @return
     */
    @PostMapping("/devicemodel/add")
    public Integer addDeviceModel(@RequestBody DeviceModelUpdateSaveVo deviceModelUpdateSaveVo ){
        return  deviceModelService.addDeviceModel(deviceModelUpdateSaveVo);
    }

    /**
     * 修改一条物模型数据
     * path: /api/device/devicemodel/upd
     * @param deviceModelUpdateSaveVo
     * @return
     */
    @PostMapping("/devicemodel/upd")
    public Integer updateDeviceModel(@RequestBody DeviceModelUpdateSaveVo deviceModelUpdateSaveVo){
        return deviceModelService.updateDeviceModel(deviceModelUpdateSaveVo);
    }

    /**
     * 删除一条物模型数据
     * path: /api/device/devicemodel/del/{dmid}
     * @param dmid
     * @return
     */
    @PostMapping("devicemodel/del/{dmid}")
    public Integer delDeviceModel(@PathVariable Long dmid){
        return deviceModelService.delDeviceModelById(dmid);
    }

}
