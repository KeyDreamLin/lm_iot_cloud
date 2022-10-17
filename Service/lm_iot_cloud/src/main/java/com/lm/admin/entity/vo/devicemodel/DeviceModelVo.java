package com.lm.admin.entity.vo.devicemodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备物模型数据 - mysql
 * @author Lm
 * @date 2022/10/12 9:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceModelVo implements Serializable {
    // 对应的设备sn
    private String sn;
}
