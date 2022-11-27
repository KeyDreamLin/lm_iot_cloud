package com.lm.admin.service.devicemodeltype;

import com.lm.admin.entity.pojo.devicemodeltype.DeviceModelType;
import com.lm.admin.mapper.mysql.devicemodeltype.DeviceModelTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物模型类型表 mapper 服务接口实现类
 * @author Lm
 * @date 2022/11/25 17：:2
 */
@Service
@Slf4j
public class DeviceModelTypeServiceImpl implements IDeviceModelTypeService {
    @Autowired
    private DeviceModelTypeMapper deviceModelTypeMapper;

    /**
     * 获取物模型数据类型列表
     * @return
     */
    @Override
    public List<DeviceModelType> getDeviceModelTypeList() {
        return deviceModelTypeMapper.findDeviceModelTypeList();
    }
}