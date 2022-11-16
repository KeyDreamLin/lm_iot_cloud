package com.lm.admin.entity.vo.devicestrategy;

import com.lm.admin.config.mybatis.annotation.FieldFill;
import com.lm.admin.config.mybatis.annotation.IdType;
import com.lm.admin.config.mybatis.annotation.TableField;
import com.lm.admin.config.mybatis.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * 设备策略 用于保存策略信息 - mysql
 * @author Lm
 * @date 2022/11/16 16:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceStrategySaveVo implements Serializable {
    // 策略id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 策略名称
    private String name;

    // 策略描述
    private String describe;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
