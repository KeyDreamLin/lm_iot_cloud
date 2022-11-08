package com.lm.admin.mapper.tdengine;

import com.lm.admin.entity.pojo.devicecmddata.DeviceCmdData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.lm.admin.entity.bo.device.DeviceDataTdBo;

import java.util.List;


/**
 * 设备数据 Mapper
 *
 * @author Lm
 * @since 2022-10-02
 */
@Mapper
public interface DeviceDataMapper {

    /**
     * 查询用户最新的数据 多个数据
     * @param sn 设备sn码
     * @param identifierStr 标识符字符串 'tag1','tag2','tag3'
     * @param identifierCount 多少个标识符
     * @return List<DeviceDataTdBo>
     */
    List<DeviceDataTdBo> fineDeviceDatas(@Param("sn") String sn , @Param("identifierStr") String identifierStr, @Param("identifierCount") Integer identifierCount);

    /**
     * 查询用户最新的数据 查询一个数据 传入一个标签
     * @param sn 设备sn码
     * @param identifierStr 标识符字符串 'tag1'
     * @param identifierCount 多少个标识符
     * @return List<DeviceDataTdBo>
     */
    DeviceDataTdBo fineDeviceData(@Param("sn") String sn , @Param("identifierStr") String identifierStr, @Param("identifierCount") Integer identifierCount);


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

    /**
     * 保存设备命令信息
     * @param tableName 表名
     * @param sn 设备sn
     * @param deviceCmdData 设备命令数据
     * @param deviceCmdData 设备命令数据
     * @return
     */
    int saveDeiceCmd(@Param("tableName")String tableName,
                     @Param("sn")String sn,
                     @Param("dcd") DeviceCmdData deviceCmdData);

    int delDeiceCmd(@Param("dcd") DeviceCmdData deviceCmdData);
    /**
     * 查询设备cmd日记 根据cmdID 查询一条
     * @param cmdID
     * @return
     */
    DeviceCmdData getDeviceCmdDataByCmdId(@Param("cmdID") String cmdID);


}
