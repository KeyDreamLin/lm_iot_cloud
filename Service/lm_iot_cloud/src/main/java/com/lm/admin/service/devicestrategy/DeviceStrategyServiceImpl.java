package com.lm.admin.service.devicestrategy;

import com.lm.admin.entity.bo.devicestrategy.DeviceStrategyInfoBo;
import com.lm.admin.entity.bo.devicestrategy.DeviceStrategyListPageBo;
import com.lm.admin.entity.dto.devicestrategy.DeviceStrategyDto;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategyPageVo;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategySaveVo;
import com.lm.admin.entity.vo.devicestrategy.DeviceStrategyUpdateVo;
import com.lm.admin.mapper.mysql.device.DeviceStrategyMapper;
import com.lm.admin.utils.mybiats.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 策略服务实现类
 * @author Lm
 * @date 2022/11/6 15:17
 */
@Service
@Slf4j
public class DeviceStrategyServiceImpl implements IDeviceStrategyService {
    @Autowired
    private DeviceStrategyMapper deviceStrategyMapper;

    @Override
    public List<DeviceStrategyDto> getAllDeviceStrategyDto() {
        return deviceStrategyMapper.findAllDeviceStrategyDto();
    }

    /**
     * 策略 分页 模糊 查询
     * @param deviceStrategyPageVo
     * @return
     */
    @Override
    public Pager<DeviceStrategyListPageBo> getDeviceStrategyPage(DeviceStrategyPageVo deviceStrategyPageVo) {
        Pager<DeviceStrategyListPageBo> pager = new Pager<>();
        // 先查询总条数
        pager.setTotalCount(deviceStrategyMapper.findDeviceStrategyCount());
        // 然后运算出总页数
        // 运算出总页数 总条数除以当前页条数 算出总页数
        if(pager.getTotalCount() % deviceStrategyPageVo.getPageSize() ==0)
            //如果总数据量刚好被当前页面大小整除，那就可以直接相除算出页数
            pager.setTotalPageNum(pager.getTotalCount() / deviceStrategyPageVo.getPageSize());
        else {
            //如果总数据量不能被当前页面大小整除，那就相除后再加上1，这样能保证
            //多余的数据页面会显示出来
            pager.setTotalPageNum(pager.getTotalCount() / deviceStrategyPageVo.getPageSize()+1);
        }

        pager.setPageIndex(deviceStrategyPageVo.getPageIndex());
        pager.setPageSize(deviceStrategyPageVo.getPageSize());

        // 查询
        List<DeviceStrategyListPageBo> db_deicePage = deviceStrategyMapper.findDeviceStrategyPage(
                ((pager.getPageIndex() - 1) * pager.getPageSize()),
                pager.getPageSize(),
                deviceStrategyPageVo.getKeyword()
        );

        pager.setRecords(db_deicePage);
        return pager;
    }

    /**
     * 根据策略id查询策略信息
     * @param Sid
     * @return
     */
    @Override
    public DeviceStrategyInfoBo getDeviceStrategyById(Long Sid) {
        return deviceStrategyMapper.findDeviceStrategyById(Sid);
    }


    /**
     * 更新策略的信息
     * @param deviceStrategyUpdateVo
     * @return
     */
    @Override
    public int updateDeviceStrategy(DeviceStrategyUpdateVo deviceStrategyUpdateVo) {
        if(deviceStrategyUpdateVo==null){
            return -1;
        }
        return deviceStrategyMapper.updateDeviceStrategy(deviceStrategyUpdateVo);
    }

    /**
     * 平台设备策略总数
     *
     * @return
     */
    @Override
    public Integer getDeviceStrategyAllCount() {
        return deviceStrategyMapper.findDeviceStrategyAllCount();
    }

    /**
     * 平台策略启用的数量
     *
     * @return
     */
    @Override
    public Integer getOpenDeviceStrategyCount() {
        return deviceStrategyMapper.findOpenDeviceStrategyCount();
    }

    /**
     * 添加一条策略信息
     * @param deviceStrategySaveVo
     * @return
     */
    @Override
    public int addDeviceStrategy(DeviceStrategySaveVo deviceStrategySaveVo) {
        if(deviceStrategySaveVo==null){
            return -1;
        }
        return deviceStrategyMapper.addDeviceStrategy(deviceStrategySaveVo);
    }


}
