package com.lm.admin.entity.pojo.device;

import java.util.Date;

import com.lm.admin.config.mybatis.annotation.FieldFill;
import com.lm.admin.config.mybatis.annotation.IdType;
import com.lm.admin.config.mybatis.annotation.TableField;
import com.lm.admin.config.mybatis.annotation.TableId;
import lombok.*;

/**
 * 设备信息 - mysql
 * @author Lm
 * @since 2022-09-23
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device implements java.io.Serializable  {

    // 主键
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 设备名称
    private String name;

    // 设备唯一号 可以自动生成 也可以手写
    private String sn;

    // 设备传输秘钥
    private String secretKey;

    // 局域网的id
    private String localAddress;

    // 发布状态 0 禁用 1未禁用
    private Integer status;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
