package com.lm.admin.controller.device_api.devicestrategy;

import com.lm.admin.controller.device_api.DeviceBaseController;
import com.lm.admin.entity.bo.devicestrategy.DeviceStrategyListPageBo;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategyPageVo;
import com.lm.admin.service.devicestrategy.DeviceStrategyServiceImpl;
import com.lm.admin.utils.mybiats.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * api:/api/device/***
 * @author Lm
 * @date 2022/11/10 16:52
 */
@Slf4j
@RestController
public class DeviceStrategyController extends DeviceBaseController {
    @Autowired
    private DeviceStrategyServiceImpl deviceStrategyService;

    /**
     * 策略 分页 模糊 查询
     * api: /api/device/devicestrategy/page
     * @param deviceStrategyPageVo
     * @return
     */
    @PostMapping("/devicestrategy/page")
    public Pager<DeviceStrategyListPageBo> page(@RequestBody DeviceStrategyPageVo deviceStrategyPageVo){
        return deviceStrategyService.getDeviceStrategyPage(deviceStrategyPageVo);
    }

}
