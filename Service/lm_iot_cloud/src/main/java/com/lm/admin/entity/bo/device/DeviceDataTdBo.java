package com.lm.admin.entity.bo.device;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
* DeviceDataTdBo td设备数据返回
* 创建人:Lm<br/>
* 时间：2022-09-23 <br/>
*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDataTdBo implements java.io.Serializable  {
    // 时间主键
    private Date ts;
    // 设备标识符
    private String identifier;
    // 数值
    private String val;
}