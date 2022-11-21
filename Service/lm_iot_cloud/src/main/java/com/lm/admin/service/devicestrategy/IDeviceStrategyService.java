package com.lm.admin.service.devicestrategy;

import com.lm.admin.entity.bo.devicestrategy.DeviceStrategyInfoBo;
import com.lm.admin.entity.bo.devicestrategy.DeviceStrategyListPageBo;
import com.lm.admin.entity.dto.devicestrategy.DeviceStrategyDto;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategyPageVo;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategySaveVo;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategyUpdateVo;
import com.lm.admin.utils.mybiats.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 策略服务接口类
 * @author Lm
 * @date 2022/11/6 15:17
 */
public interface IDeviceStrategyService {
    /**
     * 获取所有的设备策略
     * @return
     */
    List<DeviceStrategyDto> getAllDeviceStrategyDto();
    /**
     * 策略 分页 模糊 查询
     * @param deviceStrategyPageVo
     * @return
     */
    Pager<DeviceStrategyListPageBo> getDeviceStrategyPage(DeviceStrategyPageVo deviceStrategyPageVo);

    /**
     * 根据策略id查询策略信息
     * @param Sid
     * @return
     */
    DeviceStrategyInfoBo getDeviceStrategyById(Long Sid);


    /**
     * 添加一条策略信息
     * @param deviceStrategySaveVo
     * @return
     */
    int addDeviceStrategy(DeviceStrategySaveVo deviceStrategySaveVo);

    /**
     * 更新策略的数据 
     * @param deviceStrategyUpdateVo
     * @return
     */
    int updateDeviceStrategy(DeviceStrategyUpdateVo deviceStrategyUpdateVo);

    /**
     * 平台设备策略总数
     * @return
     */
    Integer getDeviceStrategyAllCount();

    /**
     * 平台策略启用的数量
     * @return
     */
    Integer getOpenDeviceStrategyCount();
}
