package com.lm.admin.controller.device_api.devicestrategy;

import com.lm.admin.controller.device_api.DeviceBaseController;
import com.lm.admin.entity.bo.devicestrategy.DeviceStrategyInfoBo;
import com.lm.admin.entity.bo.devicestrategy.DeviceStrategyListPageBo;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategyPageVo;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategySaveVo;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategyUpdateVo;
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

    /**
     * 根据策略id获取策略信息
     * api: /api/device/devicestrategy/{sid}
     * @param id 策略id
     * @return
     */
    @PostMapping("/devicestrategy/{id}")
    public DeviceStrategyInfoBo getInfoById(@PathVariable Long id){
        return deviceStrategyService.getDeviceStrategyById(id);
    }

    /**
     * 更新策略信息
     * @param deviceStrategyUpdateVo
     * @return
     */
    @PostMapping("/devicestrategy/upd")
    public int update(@RequestBody DeviceStrategyUpdateVo deviceStrategyUpdateVo){
        return deviceStrategyService.updateDeviceStrategy(deviceStrategyUpdateVo);
    }

    /**
     *
     * @param deviceStrategySaveVo
     * @return
     */
    @PostMapping("/devicestrategy/add")
    public int add(@RequestBody DeviceStrategySaveVo deviceStrategySaveVo){
        return deviceStrategyService.addDeviceStrategy(deviceStrategySaveVo);
    }
}
