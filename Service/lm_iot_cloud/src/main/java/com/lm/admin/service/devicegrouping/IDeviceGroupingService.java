package com.lm.admin.service.devicegrouping;


import com.lm.admin.entity.pojo.devicegrouping.DeviceGrouping;
import com.lm.admin.entity.vo.devicegrouping.DeviceGroupingPageVo;
import com.lm.admin.mapper.mysql.device.DeviceGroupingMapper;
import com.lm.admin.utils.mybiats.Pager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  服务实现类
*
* @author Lm
* @since 2022-09-23
*/
@Service
@Slf4j
public class IDeviceGroupingService implements DeviceGroupingServiceImpl {
    @Autowired
    private DeviceGroupingMapper deviceGroupingMapper;

    /**
     * 分页查询
     *
     * @param deviceGroupingPageVo 分页对象
     * @return
     */
    @Override
    public Pager<DeviceGrouping> getDeviceGroupingPager(DeviceGroupingPageVo deviceGroupingPageVo) {
        Pager<DeviceGrouping> pager = new Pager<>();
        // 先查询总条数
        pager.setTotalCount(deviceGroupingMapper.findDeviceGroupingCount());
        // 然后运算出总页数
        // 运算出总页数 总条数除以当前页条数 算出总页数
        if(pager.getTotalCount() % deviceGroupingPageVo.getPageSize() ==0)
            //如果总数据量刚好被当前页面大小整除，那就可以直接相除算出页数
            pager.setTotalPageNum(pager.getTotalCount() / deviceGroupingPageVo.getPageSize());
        else {
            //如果总数据量不能被当前页面大小整除，那就相除后再加上1，这样能保证
            //多余的数据页面会显示出来
            pager.setTotalPageNum(pager.getTotalCount() / deviceGroupingPageVo.getPageSize()+1);
        }

        pager.setPageIndex(deviceGroupingPageVo.getPageIndex());
        pager.setPageSize(deviceGroupingPageVo.getPageSize());

        // 查询
        List<DeviceGrouping> db_deicePage = deviceGroupingMapper.findDeviceGroupingPage(
                ((pager.getPageIndex() - 1) * pager.getPageSize()),
                pager.getPageSize(),
                deviceGroupingPageVo.getKeyword()
        );

        pager.setRecords(db_deicePage);
        return pager;
    }
}
