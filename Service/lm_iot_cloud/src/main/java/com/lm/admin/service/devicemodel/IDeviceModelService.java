package com.lm.admin.service.devicemodel;

import com.lm.admin.entity.bo.device.DeviceModelAndNewDataBo;
import com.lm.admin.entity.bo.devicemodel.DeviceModelSelectBo;
import com.lm.admin.entity.vo.devicemodel.DeviceModelUpdateSaveVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备物模型接口
 * @author Lm
 * @date 2022/10/8 16:48
 */

public interface IDeviceModelService {

    /**
     * 获取设备物模型列表
     * @param sn 设备sn码
     * @return List<DeviceModel>
     */
    List<DeviceModelAndNewDataBo> getDeiceModelBySn(String sn);

    /**
     * 给策略下拉框中的标识符传递数据
     * @param sn
     * @return
     */
    List<DeviceModelSelectBo> getDeviceModelSelectBySn(String sn);


    /**
     * 查询有多少条物模型数据
     * @return
     */
    Integer getDeviceModelAllCount();

    /**
     * 查询今天新增了多少条物模型数据
     * @return
     */
    Integer getThisDayNewDeviceModelCount();

    /**
     * 添加一条物模型数据
     * @param deviceModelUpdateSaveVo
     * @return
     */
    int addDeviceModel(DeviceModelUpdateSaveVo deviceModelUpdateSaveVo);


    /**
     * 修改一条物模型数据
     * @param deviceModelUpdateSaveVo
     * @return
     */
    int updateDeviceModel(DeviceModelUpdateSaveVo deviceModelUpdateSaveVo);


    /**
     * 根据id删除一条物模型数据
     * @param mid
     * @return
     */
    int delDeviceModelById(Long mid);

    /**
     * 根据设备id删除一条或者多条物模型数据  不提供给http
     * @param did 设备id
     * @return
     */
    int delDeviceModelByDeviceId(Long did);

}
