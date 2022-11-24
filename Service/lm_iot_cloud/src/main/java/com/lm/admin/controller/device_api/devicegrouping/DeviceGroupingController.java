package com.lm.admin.controller.device_api.devicegrouping;

import com.lm.admin.controller.device_api.DeviceBaseController;
import com.lm.admin.entity.pojo.devicegrouping.DeviceGrouping;
import com.lm.admin.entity.vo.devicegrouping.DeviceGroupingPageVo;
import com.lm.admin.service.devicegrouping.IDeviceGroupingService;
import com.lm.admin.utils.mybiats.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 设备分组接口
 * api:/api/device/***
 * @author Lm
 * @date 2022/11/11 20:43
 */
@Slf4j
@RestController
public class DeviceGroupingController extends DeviceBaseController {
    @Autowired
    private IDeviceGroupingService deviceGroupingService;


    /**
     * 分分组列表
     * path: /api/device/devicegrouping/list
     * @return 分组列表
     */
    @PostMapping("/devicegrouping/list")
    public List<DeviceGrouping> deviceGroupingList(){
        return deviceGroupingService.getDeviceGroupingList();
    }

}
