package com.lm.admin.entity.dto.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 网关实体 client传过来的数据 读取
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDataDto implements java.io.Serializable {
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
}
