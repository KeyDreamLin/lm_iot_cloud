package com.lm.admin.controller.device_api.devicemodeltype;

import com.lm.admin.controller.device_api.DeviceBaseController;
import com.lm.admin.entity.pojo.devicemodeltype.DeviceModelType;
import com.lm.admin.service.devicemodeltype.DeviceModelTypeServiceImpl;
import com.lm.admin.service.devicemodeltype.IDeviceModelTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 设备物模型数据类型接口
 * api:/api/device/***
 * @author Lm
 * @date 2022/11/25 20:36
 */
@Slf4j
@RestController
public class DeviceModelTypeController extends DeviceBaseController {
    @Autowired
    private DeviceModelTypeServiceImpl deviceModelTypeService ;

    /**
     * 获取物模型类型数据
     * api: /api/device/devicemodeltype/list
     * @return
     */
    @PostMapping("/devicemodeltype/list")
    public List<DeviceModelType> getDeviceModelTypeList(){
        return deviceModelTypeService.getDeviceModelTypeList();
    }
}
