package com.lm.admin.entity.dto.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 设备数据上报接收类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceNewDataDto implements java.io.Serializable {
    // 请求、响应类型
    private Integer t;
    // 设备sn 设备唯一标识
    private String sn;
    // 客户端上报设备数据
    private Map<String, String> data;
    // 客户端上报数据的时间 采用时间戳 毫秒 - 理论上允许用户自带时间戳但是没公开
    private Long ts;
}
