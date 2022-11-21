package com.lm.admin.entity.bo.device;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 设备信息 用于展示下拉框的 mysql
 * @author Lm
 * @date 2022/11/17 15:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceSelectBo implements java.io.Serializable  {
    // 设备名称
    private String name;
    // 设备唯一号 可以自动生成 也可以手写
    private String sn;
}
