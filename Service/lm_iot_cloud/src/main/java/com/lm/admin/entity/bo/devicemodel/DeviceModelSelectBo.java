package com.lm.admin.entity.bo.devicemodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 设备物模型数据 中的 标识符和名称 用于前端下拉框中的策略 mysql
 * @author Lm
 * @date 2022/11/17 16:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceModelSelectBo  implements Serializable {

    // 数据名称
    private String name;

    // 标识符 用于设备数据上报后查询对应的属性名称
    private String identifier;

    // 数据 类型 int string bool、、
    private String dataType;
    
    // 数据 类型描述 bool可以用
    private String dataSpecs;

}
