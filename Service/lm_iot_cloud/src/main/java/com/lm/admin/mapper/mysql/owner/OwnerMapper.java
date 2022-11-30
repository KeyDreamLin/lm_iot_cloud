package com.lm.admin.mapper.mysql.owner;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 中间表的 mapper
 * @author Lm
 * @date 2022/11/24 21:18
 */
@Mapper
public interface OwnerMapper {
    // ------------------- 用户拥有设备表 --------------------
    /**
     * 根据设备id删除 用户 拥有 设备表
     * @param deviceId
     * @return mysql row
     */
    Integer delUserOwnerDeviceByDid(@Param("device_id") Long deviceId);

    /**
     * 根据用户id删除 用户 拥有 设备表
     * @param userId 用户id
     * @return mysql row
     */
    Integer delUserOwnerDeviceByUid(@Param("user_id")Long userId);

    /**
     * 添加一条记录 用户 拥有 设备表
     * @param userId  用户id
     * @param deviceId 设备id
     * @return mysql row
     */
    Integer addUserOwnerDevice(@Param("user_id") Long userId, @Param("device_id") Long deviceId);

    // ------------------- 用户拥有设备分组表 -----------------
    /**
     * 根据设备分组id删除 用户 拥有 设备分组
     * @param groupingId 分组id
     * @return mysql row
     */
    Integer delUserOwnerGroupingByGid(@Param("grouping_id") Long groupingId);

    /**
     * 根据用户id删除 用户 拥有 设备分组
     * @param userId  用户id
     * @return mysql row
     */
    Integer delUserOwnerGroupingByUid(@Param("user_id") Long userId);

    /**
     * 添加一条 用户 拥有 设备分组
     * @param userId  用户id
     * @param groupingId 分组id
     * @return mysql row
     */
    Integer addUserOwnerGrouping(@Param("user_id") Long userId, @Param("grouping_id") Long groupingId);


    // ------------------- 用户拥有设备策略表 -----------------
    /**
     * 根据设备策略id删除 用户 拥有 设备策略表
     * @param strategyId  策略id
     * @return mysql row
     */
    Integer delUserOwnerStrategyBySid(@Param("strategy_id") Long strategyId);

    /**
     * 根据用户id删除 用户 拥有 设备策略表
     * @param userId
     * @return mysql row
     */
    Integer delUserOwnerStrategyUid(@Param("user_id") Long userId);

    /**
     * 添加一条记录 用户 拥有 设备策略表
     * @param userId 用户id
     * @param strategyId 策略id
     * @return mysql row
     */
    Integer addUserOwnerStrategy(@Param("user_id") Long userId, @Param("strategy_id") Long strategyId);


    // ------------------- 分组拥有设备表 -----------------
    /**
     * 根据设备id删除 分组 拥有 设备表
     * @param deviceId 设备id
     * @return mysql row
     */
    Integer delGroupingByDid(@Param("device_id") Long deviceId);

    /**
     * 根据分组id删除 分组 拥有 设备表
     * @param groupingId  分组id
     * @return mysql row
     */
    Integer delGroupingByGid(@Param("grouping_id") Long groupingId);

    /**
     * 添加一条记录 分组 拥有 设备表
     * @param groupingId  分组id
     * @param deviceId  设备id
     * @return
     */
    Integer addGroupingOwnerDevice(@Param("grouping_id") Long groupingId,@Param("device_id") Long deviceId);

    /**
     * 添加多条记录 分组 拥有 设备表
     * @param groupingId  分组id
     * @param deviceIds  设备id列表
     * @return
     */
    Integer addGroupingOwnerDevices(@Param("grouping_id") Long groupingId,@Param("device_id_s") List<Long> deviceIds);

    /**
     * 查询分组拥有的设备  根据id
     * @param groupingId 分组id
     * @return
     */
    List<Long> findGroupingOwnerDeviceById(@Param("grouping_id") Long groupingId);
    // END
}
