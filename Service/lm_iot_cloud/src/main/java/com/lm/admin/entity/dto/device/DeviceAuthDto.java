package com.lm.admin.entity.dto.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
* DeviceDto参数类 用于返回给tcp、mqtt服务鉴权用的
* 创建人:Lm<br/>
* 时间：2022-09-23 <br/>
*
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceAuthDto implements Serializable {
    // 设备唯一号 可以自动生成 也可以手写
    private String sn;
    // 设备传输秘钥
    private String secretKey;
}
