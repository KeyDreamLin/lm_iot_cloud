package com.lm.admin.mapper.tdengine;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.lm.admin.entity.bo.device.DeviceDataBo;

import java.util.List;


/**
 * 设备数据 Mapper
 *
 * @author Lm
 * @since 2022-10-02
 */
@Mapper
public interface DeviceDataMapper {

    List<DeviceDataBo> fineDeviceDatas(@Param("sn") String sn);

    /**
     * 保存设备发过来的数据
     * @param tableName 表名
     * @param sn 设备sn码
     * @param identifier 标识符
     * @param val 值
     * @return
     */
    int saveDeviceData(@Param("tableName")String tableName,
                       @Param("sn")String sn,
                       @Param("identifier")String identifier,
                       @Param("val")String val
                       );
}
