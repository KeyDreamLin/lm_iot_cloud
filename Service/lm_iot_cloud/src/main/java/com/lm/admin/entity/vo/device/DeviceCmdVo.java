package com.lm.admin.entity.vo.device;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * html服务发送给设备的类 Bo转Dto发送给CloudR
 * @author Lm
 * @date 2022/10/9 16:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCmdVo {

    // 设备sn码 必填
    private String sn;
    // 传感器标识符 可以为“”
    private String identifier;
    // 一般用于命令请求
    private String data;

}
