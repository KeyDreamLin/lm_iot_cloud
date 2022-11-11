package com.lm.admin.entity.bo.devicestrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备策略 用于策略列表展示 不需要全部放出来 - mysql
 * @author Lm
 * @date 2022/11/6 15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceStrategyListPageBo implements Serializable {
    // 策略id
    private Long id;
    // 策略名称
    private String name;
    // 策略描述
    private String describe;
    // 是否启用 0 不启用 1 启用
    private Integer status;
    // 创建时间
    private Date createTime;
}
