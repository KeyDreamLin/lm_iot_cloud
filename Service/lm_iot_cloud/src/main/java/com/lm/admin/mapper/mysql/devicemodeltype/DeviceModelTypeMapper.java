package com.lm.admin.mapper.mysql.devicemodeltype;

import com.lm.admin.entity.pojo.devicemodeltype.DeviceModelType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 物模型类型表 mapper mysql
 * @author Lm
 * @date 2022/11/25 16:53
 */
@Mapper
public interface DeviceModelTypeMapper{
    List<DeviceModelType> findDeviceModelTypeList();
}