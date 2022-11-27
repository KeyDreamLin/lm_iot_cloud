package com.lm.admin.service.devicemodeltype;

import com.lm.admin.entity.pojo.devicemodeltype.DeviceModelType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 物模型类型表 mapper 服务接口
 * @author Lm
 * @date 2022/11/25 17：:2
 */
public interface IDeviceModelTypeService {
    /**
     * 获取物模型数据类型列表
     * @return
     */
    List<DeviceModelType> getDeviceModelTypeList();
}