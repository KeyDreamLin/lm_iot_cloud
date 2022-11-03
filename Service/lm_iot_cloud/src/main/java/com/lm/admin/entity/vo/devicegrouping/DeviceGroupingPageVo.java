package com.lm.admin.entity.vo.devicegrouping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 设备分组 分页查询
 * @author Lm
 * @date 2022/10/31 15:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceGroupingPageVo implements java.io.Serializable  {
    // 当前页 从一开始所以要减一 (pageIndex - 1) * pageSize
    private Integer pageIndex = 1;
    // 每页大小 一页有多少条数据
    private Integer pageSize = 10;
    // 搜索关键字 sn(条件查询必须等于)  或者  设备名称(模糊搜索)
    private String keyword = null;
}
