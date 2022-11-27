package com.lm.admin.entity.vo.device;

import com.lm.admin.config.mybatis.annotation.FieldFill;
import com.lm.admin.config.mybatis.annotation.IdType;
import com.lm.admin.config.mybatis.annotation.TableField;
import com.lm.admin.config.mybatis.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 设备信息 用于更新 - mysql
 * @author Lm
 * @since 2022-11-24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceUpdateVo implements java.io.Serializable  {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 设备名称
    private String name;

    // 本地id地址
    private String localAddress;

    // 发布状态 0 禁用 1未禁用
    private Integer status;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
