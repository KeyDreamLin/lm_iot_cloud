package com.lm.admin.mapper.mysql.devicegrouping;

import com.lm.admin.entity.pojo.device.Device;
import com.lm.admin.entity.pojo.devicegrouping.DeviceGrouping;
import com.lm.admin.entity.vo.devicegrouping.DeviceGroupingUpdateAndSaveVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备分组 - mysql
 * @author Lm
 * @date 2022/10/31 15:42
 */
public interface BaseDeviceGroupingMapper {

    /**
     * 查询全部的设备分组数量
     * @return
     */
    Integer findDeviceGroupingCount();

    List<DeviceGrouping> findDeviceGroupingList(@Param("user_id") Long userId);

    /**
     * 添加一个设备分组
     * @param deviceGrouping
     * @return
     */
    Integer addDeviceGrouping(@Param("dg") DeviceGroupingUpdateAndSaveVo deviceGrouping);

    /**
     * 修改设备分组信息
     * @param deviceGrouping
     * @return
     */
    Integer updDeviceGrouping(@Param("dg") DeviceGroupingUpdateAndSaveVo deviceGrouping);

    /**
     * 删除设备分组
     * @param groupingId
     * @return
     */
    Integer delDeviceGrouping(@Param("grouping_id")Long groupingId);

    /**
     * 根据分组id查询到分组
     * @param userId  这个是普通用户查询自己的分组用的，管理员随便传个值就行
     * @param groupingId  分组id
     * @return
     */
    DeviceGrouping findDeviceGroupingByGid(@Param("user_id") Long userId, @Param("grouping_id")Long groupingId);

}
