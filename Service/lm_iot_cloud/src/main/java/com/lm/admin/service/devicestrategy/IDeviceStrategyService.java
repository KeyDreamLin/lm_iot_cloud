package com.lm.admin.service.devicestrategy;

import com.lm.admin.entity.bo.devicestrategy.DeviceStrategyListPageBo;
import com.lm.admin.entity.dto.devicestrategy.DeviceStrategyDto;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategyPageVo;
import com.lm.admin.utils.mybiats.Pager;
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

}
