package com.lm.admin.entity.pojo.devicemodel;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备物模型数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceModel implements Serializable {

    // 主键 雪花
    private Long id;

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

    // 创建时间

    private Date createTime;
    // 更新时间
    private Date updateTime;
}
