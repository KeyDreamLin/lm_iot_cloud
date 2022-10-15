package com.lm.admin.mapper.mysql.permission;

import com.lm.admin.entity.dto.device.DeviceAuthDto;
import com.lm.admin.entity.pojo.device.Device;
import com.lm.admin.entity.pojo.permission.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限数据 Mapper - mysql
 *
 * @author Lm
 * @since 2022-09-23
 */
@Mapper
public interface PermissionMapper {
    /**
     * 查询所有权限和菜单
     * @return
     */
    List<Permission> findPermission();
}
