package com.lm.admin.entity.bo.devicestrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备策略 信息 - mysql
 * @author Lm
 * @date 2022/11/6 15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceStrategyInfoBo implements Serializable {
    // 策略id
    private Long id;
    // 策略名称
    private String name;
    // 策略描述
    private String describe;
    // 触发条件表达式字符串
    private String triggerStr;
    // 执行动作字符串
    private String actionStr;
    // 是否启用 0 不启用 1 启用
    private Integer status;
}
