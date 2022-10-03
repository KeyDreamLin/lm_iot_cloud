package com.lm.admin.service.device;


import com.lm.admin.entity.dto.device.DeviceDto;
import com.lm.admin.mapper.mysql.device.DeviceMapper;
import com.lm.admin.mapper.tdengine.DeviceDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
*  服务实现类
*
* @author Lm
* @since 2022-09-23
*/
@Service
public class IDeviceService implements DeviceServiceImpl {
    // 设备信息
    @Autowired
    private DeviceMapper deviceMapper;

    // tde的设备数据库
    @Autowired
    private DeviceDataMapper deviceDataMapper;

    // 设置日期格式
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");



    /**
     * 根据sn码查询到设备信息
     * @param sn
     * @return
     */
    @Override
    public DeviceDto getDeviceBySn(String sn) {
        return deviceMapper.findDeviceBySn(sn);
    }

    /**
     * 保存设备数据
     *
     * @param sn sn码
     * @param dataMap 设备数据集合
     * @return
     */
    @Override
    public int saveDeviceData(String sn, Map<String, String> dataMap) {
        AtomicReference<Integer> saveCount = new AtomicReference<>(0);
        dataMap.forEach((k,v)->{
            saveCount.set(
                    saveCount.get()+
                            // 拼接 表名 sn_yyyyMMdd
                    deviceDataMapper.saveDeviceData(sn + "_" + simpleDateFormat.format(new Date()), sn, k, v)
            );
        });
        return saveCount.get();
    }
}
