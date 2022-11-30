package com.lm.admin.entity.vo.devicegrouping;

import com.lm.admin.config.mybatis.annotation.FieldFill;
import com.lm.admin.config.mybatis.annotation.IdType;
import com.lm.admin.config.mybatis.annotation.TableField;
import com.lm.admin.config.mybatis.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 设备分组 更新和添加
 * @author Lm
 * @date 2022/11/28 15:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceGroupingUpdateAndSaveVo  implements java.io.Serializable  {
    // 分组主键id
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 分组名称
    private String name;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    // 用户id 用于管理员添加分组的  管理员授权给用户的
    private Long userId;

    // 设备分组拥有的设备列表   前端修改后的设备分组列表 拥有的设备
    private List<Long> deviceIds;
}
