package com.lm.admin.entity.pojo.devicegrouping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 设备分组实体 - mysql
 * @author Lm
 * @date 2022/10/31 15:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceGrouping implements java.io.Serializable  {
    // 分组主键id
    private Long id;
    // 分组名称
    private String name;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
