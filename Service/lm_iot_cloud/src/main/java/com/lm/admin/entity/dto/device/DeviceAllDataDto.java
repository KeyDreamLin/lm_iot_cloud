package com.lm.admin.entity.dto.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Tcp协议实体 client传过来的设备鉴权、设备数据上报  读取  TCP所有数据的一个总和
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceAllDataDto implements java.io.Serializable {
    // 请求、响应类型
    private Integer t;
    // 设备sn 设备唯一标识
    private String sn;
    // 设备传输秘钥
    private String secretKey;
    // 客户端上报设备数据
    private Map<String, String> data;
    // 客户端响应的状态
    private Integer status;
    // 客户端原样带回的cmdId
    private String cmdId;
    // 客户端上报数据的时间 采用时间戳 毫秒 - 理论上允许用户自带时间戳但是没公开
    private Long ts = System.currentTimeMillis();
}
