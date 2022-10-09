package com.lm.admin.entity.bo.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 设备标识符加名称加数据返回
 * @author Lm
 * @date 2022/10/8 17:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceIdentifierAndNameDataBo {
    // 时间主键
    private Date ts;
    // 设备标识符
    private String identifier;
    // 名称
    private String name;
    // 数值
    private String val;
}
