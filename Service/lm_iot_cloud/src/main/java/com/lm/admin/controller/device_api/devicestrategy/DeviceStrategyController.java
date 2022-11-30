package com.lm.admin.controller.device_api.devicestrategy;

import com.lm.admin.controller.device_api.DeviceBaseController;
import com.lm.admin.entity.bo.devicestrategy.DeviceStrategyInfoBo;
import com.lm.admin.entity.bo.devicestrategy.DeviceStrategyListBo;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategyPageVo;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategySaveVo;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategyUpdateVo;
import com.lm.admin.service.devicestrategy.DeviceStrategyServiceImpl;
import com.lm.admin.utils.mybiats.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 获取策略列表 全部
     * api: /api/device/devicestrategy/list
     * @return
     */
    @PostMapping("/devicestrategy/list")
    public List<DeviceStrategyListBo> list(){
        return deviceStrategyService.getDeviceStrategyList();
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
     * api: /api/device/devicestrategy/upd
     * @param deviceStrategyUpdateVo
     * @return
     */
    @PostMapping("/devicestrategy/upd")
    public int update(@RequestBody DeviceStrategyUpdateVo deviceStrategyUpdateVo){
        return deviceStrategyService.updateDeviceStrategy(deviceStrategyUpdateVo);
    }

    /**
     * 添加一条策略信息
     * api: /api/device/devicestrategy/add
     * @param deviceStrategySaveVo
     * @return
     */
    @PostMapping("/devicestrategy/add")
    public int add(@RequestBody DeviceStrategySaveVo deviceStrategySaveVo){
        return deviceStrategyService.addDeviceStrategy(deviceStrategySaveVo);
    }

    /**
     * 删除一条策略信息 根据策略id
     * @param strategyId 策略id
     * @return
     */
    @PostMapping("/devicestrategy/del/{strategyId}")
    public int delDeviceStrategy(@PathVariable Long strategyId){
        return deviceStrategyService.delDeviceStrategy(strategyId);
    }
    /**
     * 平台设备策略总数
     * api: /api/device/devicestrategy/allcount
     * @return
     */
    @PostMapping("/devicestrategy/allcount")
    public Integer getAllCount() {
        return deviceStrategyService.getDeviceStrategyAllCount();
    }

    /**
     * 平台策略启用的数量
     * api: /api/device/devicestrategy/opencount
     * @return
     */
    @PostMapping("/devicestrategy/opencount")
    public Integer getOpenCount() {
        return deviceStrategyService.getOpenDeviceStrategyCount();
    }

}
