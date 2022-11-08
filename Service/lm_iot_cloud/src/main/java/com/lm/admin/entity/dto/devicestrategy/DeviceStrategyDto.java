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

    // 表达式/字符串
    private String expStr;
    // 需要返回的数据
    private String retData;
    // 是否启用 0 不启用 1 启用
    private Integer status;

}
