package com.lm.admin.entity.bo.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * 设备物模型数据 和 设备的最新数据 redis+mysql
 * @author Lm
 * @date 2022/10/12 9:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceModelAndNewDataBo implements Serializable {

    // 对应的设备sn
    private String sn;

    // 图标
    private String icon;

    // 数据名称
    private String name;

    // 数据单位 值后面的参数 例->50℃
    private String unit;

    // 标识符 用于设备数据上报后查询对应的属性名称
    private String identifier;

    // 数据 类型 int string bool、、
    private String dataType;

    // 数据 类型描述 bool可以用
    private String dataSpecs;

    // 模型 类型   0是传感器 1是开关量 其他待定
    private Integer modelType;

    // 设备时间戳
    private Date ts;

    // 设备数据
    private String val;
}
