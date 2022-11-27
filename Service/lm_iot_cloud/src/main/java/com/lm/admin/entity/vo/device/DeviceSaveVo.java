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
 * 设备信息 用于保存 - mysql
 * @author Lm
 * @since 2022-11-24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceSaveVo implements java.io.Serializable  {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 设备名称
    private String name;

    // 设备唯一号 可以自动生成 也可以手写
    private String sn;

    // 设备传输秘钥
    private String secretKey;

    // 发布状态 0 禁用 1未禁用
    private Integer status;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
