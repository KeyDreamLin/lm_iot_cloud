package com.lm.admin.entity.dto.devicestrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备策略 - mysql
 * @author Lm
 * @date 2022/11/6 15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceStrategyDto implements Serializable {
    private Long id;
    // 触发条件表达式字符串
    private String triggerStr;
    // 执行动作字符串
    private String actionStr;
    // 是否启用 0 不启用 1 启用
    private Integer status;

}
