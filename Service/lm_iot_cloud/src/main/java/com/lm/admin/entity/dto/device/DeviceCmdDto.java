package com.lm.admin.entity.dto.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网关实体 client传过来的设备cmd命令响应  读取
 * @author Lm
 * @date 2022/10/10 17:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCmdDto implements java.io.Serializable {
    // 请求、响应类型
    private final Integer t = 5;
    // 命令编号
    private String cmdId;
    // 状态结果
    private Integer status;
}
