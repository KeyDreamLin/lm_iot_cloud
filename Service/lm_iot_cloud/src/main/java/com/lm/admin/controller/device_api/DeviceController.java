package com.lm.admin.controller.device_api;

import com.lm.admin.entity.bo.device.DeviceDataBo;
import com.lm.admin.entity.bo.device.DeviceIdentifierAndNameDataBo;
import com.lm.admin.entity.bo.device.DeviceListBo;
import com.lm.admin.service.device.DeviceServiceImpl;
import com.lm.common.redis.devicekey.CloudRedisKey;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author Lm
 * @date 2022/10/8 15:05
 */
@Slf4j
@RestController
@RequestMapping("/api/device")
@ResponseBody
public class DeviceController {

    @Autowired
    private DeviceServiceImpl deviceService;

    // 操作Redis
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 获取该设备的最新数据
     * path : /api/device/newData/{sn}
     * return DeviceDataBo
     **/
    @PostMapping("/newdata/{sn}")
    public List<DeviceIdentifierAndNameDataBo> newData(@PathVariable("sn") String sn){
        log.info("----->{}",sn);
        return deviceService.getDeviceNewData(sn);
    }

    /**
     * 获取设备在线的数量
     * path /api/device/onLineCount
     * @return
     */
    @PostMapping("/onLineCount")
    public Long onLineCount(){
        // 获取计数器的值 如果key不存在就是null
        Object count = redisTemplate.opsForValue().get(CloudRedisKey.DeviceOnLineCount);
        return Long.valueOf((count == null ? 0 : count).toString());
    }

//    public DeviceListBo listPage(){
//
//    }

}
