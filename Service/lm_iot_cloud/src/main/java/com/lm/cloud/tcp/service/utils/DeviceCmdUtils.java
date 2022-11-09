package com.lm.cloud.tcp.service.utils;

import com.alibaba.fastjson2.JSON;
import com.lm.admin.common.r.DeviceResultEnum;
import com.lm.admin.entity.vo.device.DeviceCmdVo;
import com.lm.admin.entity.dto.device.DeviceAllDataDto;
import com.lm.admin.entity.pojo.devicecmddata.DeviceCmdData;
import com.lm.admin.mapper.tdengine.DeviceDataMapper;
import com.lm.admin.utils.LmAssert;
import com.lm.admin.utils.SnowflakeIdWorker;
import com.lm.cloud.common.r.CloudR;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 设备命令工具类
 * @author Lm
 * @date 2022/10/11 20:28
 */
@Component
public class DeviceCmdUtils {
    // 注意时区问题 这里bean里面的ts采用时间戳的形式

    @Autowired
    private DeviceDataMapper deviceDataMapper;

    private static DeviceDataMapper sDeviceDataMapper;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");

    @PostConstruct
    private void setStatic(){
        sDeviceDataMapper = deviceDataMapper;
    }

    /**
     * 服务器请求客户端命令
     * @param deviceCmdVo  请求的数据
     * @return
     */
    public static Boolean requestCmd(DeviceCmdVo deviceCmdVo){
        // 通过sn获取id
        String dbR_idBySn = RedisDeviceUtils.getCidBySn(deviceCmdVo.getSn());
        // 通过sn获取id   id如果为空 那就是设备不在线 抛出异常
        LmAssert.isEmptyEx(dbR_idBySn, DeviceResultEnum.DEVICE_NOT_ONLINE_ERROR);

        // 通过id到对应的设备对象上下文对象 用于下发命令
        ChannelHandlerContext ctx = RedisDeviceUtils.deviceMap.get(dbR_idBySn);

        // 封装日记数据 用于保存命令日记
        DeviceCmdData db_deviceCmdData = new DeviceCmdData();
        db_deviceCmdData.setNts(System.currentTimeMillis());
        db_deviceCmdData.setCmdID(SnowflakeIdWorker.getDeviceId().toString());
        db_deviceCmdData.setData(deviceCmdVo.getData().toString());
        db_deviceCmdData.setApitag(deviceCmdVo.getIdentifier());
        // 默认的状态是设备未响应
        db_deviceCmdData.setStatus(false);

        // 如果指令中存在整数数字 就将 以 int发送
        if(isInteger(deviceCmdVo.getData())){
            // 向设备发送指令
            ctx.writeAndFlush(
                    JSON.toJSONString(
                            CloudR.Cmd(db_deviceCmdData.getCmdID(), deviceCmdVo.getIdentifier(), Integer.valueOf(deviceCmdVo.getData()))
                    )
            );
        }
        // 否则使用string TODO 判断 浮点数 float double
        else{
            ctx.writeAndFlush(
                    JSON.toJSONString(
                            CloudR.Cmd(db_deviceCmdData.getCmdID(), deviceCmdVo.getIdentifier(), deviceCmdVo.getData())
                    )
            );
        }


        // 保存命令日记
        sDeviceDataMapper.saveDeiceCmd(
                deviceCmdVo.getSn()+"_"+simpleDateFormat.format(new Date()).toString() + "_cmd",
                deviceCmdVo.getSn(),
                db_deviceCmdData
        );

        return true;
    }

    /**
     * 设备命令响应
     * @param deviceAllDataDto 设备发送给服务器的数据
     * @param sn 设备sn码
     * @return
     */
    public static Boolean responseCmd(DeviceAllDataDto deviceAllDataDto, String sn){
        if(deviceAllDataDto == null){
            return false;
        }
        if(LmAssert.isEmpty(deviceAllDataDto.getCmdId())){
            return false;
        }

        // 通过设备响应带回来的cmdID查询到设备命令日记
        DeviceCmdData deviceCmdDataByCmdId = sDeviceDataMapper.getDeviceCmdDataByCmdId(deviceAllDataDto.getCmdId());

        if(deviceCmdDataByCmdId == null){
            return false;
        }
        // 写入设备响应了服务器的指令
        deviceCmdDataByCmdId.setStatus(true);
        deviceCmdDataByCmdId.setRts(System.currentTimeMillis());

        // 先删除，这条日记
        sDeviceDataMapper.delDeiceCmd(deviceCmdDataByCmdId);

        // 重新插入一条新的日记的
        sDeviceDataMapper.saveDeiceCmd(
                sn+"_"+simpleDateFormat.format(new Date()).toString() + "_cmd",
                sn,
                deviceCmdDataByCmdId
        );
        return true;
    }


    /*方法二：推荐，速度最快
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


}
