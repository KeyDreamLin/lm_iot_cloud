package com.lm.admin.entity.vo.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备查询
 * @author Lm
 * @date 2022/11/19 16:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceIdSnVo implements java.io.Serializable  {
    private Long Gid;
    // 主键
    private Long Did;

    // 设备唯一号 可以自动生成 也可以手写
    private String sn;
}