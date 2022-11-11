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
     * 分页查询设备分组列表
     * path: /api/device/devicegroupingpage
     * @param pager
     * @return 分页数据
     */
    @PostMapping("/devicegroupingpage")
    public Pager<DeviceGrouping> deviceGroupingPage(@RequestBody DeviceGroupingPageVo pager){
        return deviceGroupingService.getDeviceGroupingPager(pager);
    }

}
