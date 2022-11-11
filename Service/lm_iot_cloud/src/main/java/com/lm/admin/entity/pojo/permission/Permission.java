package com.lm.admin.entity.pojo.permission;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 菜单管理
 * @author Lm
 * @since 2022-10-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements java.io.Serializable {

     // 主键
    private Long id;

     // 菜单名词
    private String name;

     // 菜单排序
    private Integer sorted;

     // 菜单链接
    private String path;

     // 菜单图标
    private String icon;

     // 菜单发布
    private Integer status;

     // 创建时间
    private Date createTime;

     // 更新时间
    private Date updateTime;

     // 菜单名称
    private Long pid;

     // 路径名称
    private String pathname;

     // 删除状态 0未删除 1删除
    private Integer isdelete;

     // 1菜单 2 权限
    private Integer type;

     // 代号
    private String code;

    // 子类 pid==id
    @JSONField(ordinal = 666)
    private List<Permission> children;
}
