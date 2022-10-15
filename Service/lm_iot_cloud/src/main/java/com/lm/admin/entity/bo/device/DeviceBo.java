package com.lm.admin.entity.bo.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * 设备信息
 * @author Lm
 * @date 2022/10/8 21:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceBo implements java.io.Serializable  {
    // 设备名称
    private String name;
    // 设备唯一号 可以自动生成 也可以手写
    private String sn;
    // 设备传输秘钥
    private String secretKey;
    // 局域网的id
    private String localAddress;
    // 创建时间
    private Date createTime;
    // 设备是否在线
    private Boolean isOnLine;
}
