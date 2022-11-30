package com.lm.admin.controller.device_api.devicegrouping;

import com.lm.admin.controller.device_api.DeviceBaseController;
import com.lm.admin.entity.pojo.devicegrouping.DeviceGrouping;
import com.lm.admin.entity.vo.devicegrouping.DeviceGroupingPageVo;
import com.lm.admin.entity.vo.devicegrouping.DeviceGroupingUpdateAndSaveVo;
import com.lm.admin.service.devicegrouping.IDeviceGroupingService;
import com.lm.admin.utils.mybiats.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 添加一个设备分组
     * path: /api/device/devicegrouping/add
     * @param deviceGroupingUpdateAndSaveVo
     * @return
     */
    @PostMapping("/devicegrouping/add")
    public Integer addDeviceGrouping(@RequestBody DeviceGroupingUpdateAndSaveVo deviceGroupingUpdateAndSaveVo){
        return deviceGroupingService.addDeviceGrouping(deviceGroupingUpdateAndSaveVo);
    }

    /**
     * 修改 分组信息  分组拥有设备
     * @param deviceGroupingUpdateAndSaveVo
     * @return
     */
    @PostMapping("/devicegrouping/upd")
    public Integer updDeviceGrouping(@RequestBody DeviceGroupingUpdateAndSaveVo deviceGroupingUpdateAndSaveVo){
        return deviceGroupingService.updDeviceGrouping(deviceGroupingUpdateAndSaveVo);
    }

    /**
     * 刪除 整个分组
     * @param id
     * @return
     */
    @PostMapping("/devicegroupiong/del/{id}")
    public Integer delDeviceGrouping(@PathVariable Long id){
        return deviceGroupingService.delDeviceGrouping(id);
    }
    /**
     * 查询设备分组拥有的设备 根据 分组id
     * path: /api/device/devicegrouping/ownerdevice/{id}
     * @return  设备列表
     */
    @PostMapping("/devicegrouping/ownerdevice/{id}")
    public List<Long> getGroupingOwnerDeviceById(@PathVariable Long id){
        return deviceGroupingService.getGroupingOwnerDeviceById(id);
    }

    /**
     * 根据分组id查询分组信息
     * path: /api/device/devicegrouping/querybyid/{id}
     * @param id 分组id
     * @return
     */
    @PostMapping("/devicegrouping/querybyid/{id}")
    public DeviceGrouping getDeviceGroupingById(@PathVariable Long id){
        return deviceGroupingService.getDeviceGroupingById(id);
    }



}
