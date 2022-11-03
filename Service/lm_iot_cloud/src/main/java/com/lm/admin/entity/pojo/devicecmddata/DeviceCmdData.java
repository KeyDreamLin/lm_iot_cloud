package com.lm.admin.entity.pojo.devicecmddata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备命令数据 - tdengine
 * @author Lm
 * @date 2022/10/12 9:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCmdData implements java.io.Serializable  {
    // 命令创建时间
    private Long nts;
    // 命令id
    private String cmdID ;
    // 命令下发数据
    private String data ;
    // 传感器标识符
    private String apitag;
    // 命令是否响应 0 false 命令已创建 1 true 设备已响应
    private Boolean status ;
    // 命令响应时间
    private Long rts ;
}
