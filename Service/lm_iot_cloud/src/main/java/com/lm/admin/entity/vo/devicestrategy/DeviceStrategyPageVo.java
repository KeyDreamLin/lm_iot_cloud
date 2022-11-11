package com.lm.admin.entity.vo.devicestrategy;

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
public class DeviceStrategyPageVo implements Serializable {
    // 当前页 从一开始所以要减一 (pageIndex - 1) * pageSize
    private Integer pageIndex = 1;
    // 每页大小 一页有多少条数据
    private Integer pageSize = 10;
    // 搜索关键字 sn(条件查询必须等于)  或者  设备名称(模糊搜索)
    private String keyword = null;
}
