package com.lm.admin.mapper.mysql.userowner;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户中间表的 mapper
 * @author Lm
 * @date 2022/11/24 21:18
 */
@Mapper
public interface UserOwnerMapper {
    // ------------------- 用户拥有设备表 --------------------
    // 根据设备id删除
    // 根据用户id删除
    /**
     * 添加一条记录
     * @param uid
     * @param did
     * @return
     */
    int addUserOwnerDevice(@Param("user_id") Long uid, @Param("device_id") Long did);

    // ------------------- 用户拥有设备分组表 -----------------

    // 根据设备分组id删除
    // 根据用户id删除
    // 添加一条记录

   // ------------------- 用户拥有设备策略表 -----------------

    // 根据设备策略id删除
    // 根据用户id删除
    // 添加一条记录

}
