package com.lm.admin.controller.device_api;

import com.alibaba.fastjson2.JSON;
import com.lm.admin.common.r.DeviceResultEnum;
import com.lm.admin.entity.bo.device.DeviceCmdBo;
import com.lm.admin.entity.bo.device.DeviceIdentifierAndNameDataBo;
import com.lm.admin.entity.bo.device.DeviceBo;
import com.lm.admin.entity.bo.devicemodel.DeviceModelBo;
import com.lm.admin.entity.pojo.devicemodel.DeviceModel;
import com.lm.admin.entity.vo.device.DevicePageVo;
import com.lm.admin.entity.vo.devicemodel.DeviceModelVo;
import com.lm.admin.service.device.DeviceServiceImpl;
import com.lm.admin.service.devicemodel.DeviceModelService;
import com.lm.admin.utils.LmAssert;
import com.lm.admin.utils.SnowflakeIdWorker;
import com.lm.admin.utils.mybiats.Pager;
import com.lm.cloud.common.r.CloudR;
import com.lm.cloud.tcp.service.utils.DeviceCmdUtils;
import com.lm.cloud.tcp.service.utils.RedisDeviceUtils;
import com.lm.common.redis.devicekey.CloudRedisKey;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private DeviceModelService deviceModelService;
    // 操作Redis
    @Autowired
    private StringRedisTemplate SredisTemplate;


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
        return RedisDeviceUtils.getDeviceOnLineCount();
    }

    /**
     * 查询根据sn查询设备是否在线
     * path /api/device/online/{sn}
     * @param sn
     * @return
     */
    @PostMapping("/online/{sn}")
    public Boolean isOnLineBySn(@PathVariable("sn") String sn){
        return RedisDeviceUtils.getDeviceIsOnLienBySn(sn);
    }
    /**
     * 分页查询设备列表
     * path: /api/device/page
     * @param pager
     * @return 分页数据
     */
    @PostMapping("/page")
    public Pager<DeviceBo> listPage(@RequestBody DevicePageVo pager){
        return deviceService.getDevicePager(pager);
    }
//
//    /**
//     * 根据id获取设备信息
//     * @param sn
//     * @return
//     */
//    @PostMapping("/device/{sn}")
//    public DeviceBo getDeviceBySn(@PathVariable("sn") String sn){
//        return deviceService.getDeviceBoBySn(sn);
//    }


    /**
     * 根据设备Sn查询到对应的物模型数据
     * path: /api/device/devicemodel
     * @param deviceModelVo
     * @return
     */
    @PostMapping("/devicemodel")
    public List<DeviceModelBo> getDeviceModel(@RequestBody DeviceModelVo deviceModelVo){
        return deviceModelService.getDeiceModelBySn(deviceModelVo.getSn());
    }


    /**
     * 下发设备命令
     * @param deviceCmdBo
     * @return
     */
    @PostMapping("/cmd")
    public String cmd(@RequestBody DeviceCmdBo deviceCmdBo){
        log.info("------>{}",deviceCmdBo);
        // sn码判断是否为空  空抛出异常
        LmAssert.isEmptyEx(deviceCmdBo.getSn(), DeviceResultEnum.DEVICE_SN_NULL_ERROR);
        // Data参数必须携带，值可以为空
        LmAssert.isNotNull(deviceCmdBo.getData(), DeviceResultEnum.DEVICE_DATA_NULL_ERROR);
        // 请求设备命令
        DeviceCmdUtils.requestCmd(deviceCmdBo);
        return "命令发送成功!";
    }
}
