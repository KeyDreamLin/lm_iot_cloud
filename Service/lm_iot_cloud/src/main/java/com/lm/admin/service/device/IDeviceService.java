package com.lm.admin.service.device;


import com.lm.admin.entity.bo.device.DeviceBo;
import com.lm.admin.entity.bo.device.DeviceDataBo;
import com.lm.admin.entity.bo.device.DeviceIdentifierAndNameDataBo;
import com.lm.admin.entity.dto.device.DeviceAuthDto;
import com.lm.admin.entity.pojo.device.Device;
import com.lm.admin.entity.pojo.devicemodel.DeviceModel;
import com.lm.admin.entity.vo.device.DevicePageVo;
import com.lm.admin.mapper.mysql.device.DeviceMapper;
import com.lm.admin.mapper.tdengine.DeviceDataMapper;
import com.lm.admin.service.devicemodel.DeviceModelService;
import com.lm.admin.utils.mybiats.Pager;
import com.lm.cloud.tcp.service.utils.RedisDeviceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
*  服务实现类
*
* @author Lm
* @since 2022-09-23
*/
@Service
@Slf4j
public class IDeviceService implements DeviceServiceImpl {
    // 设备信息
    @Autowired
    private DeviceMapper deviceMapper;

    // tde的设备数据库
    @Autowired
    private DeviceDataMapper deviceDataMapper;

    // 设备物模型
    @Autowired
    private DeviceModelService deviceModelService;


    // 设置日期格式
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");



    /**
     * 根据sn码查询到设备信息
     * @param sn
     * @return
     */
    @Override
    public DeviceAuthDto getDeviceBySn(String sn) {
        return deviceMapper.findDeviceBySn(sn);
    }

    /**
     * 保存设备数据
     *
     * @param sn sn码
     * @param dataMap 设备数据集合
     * @return
     */
    @Override
    public int saveDeviceData(String sn, Map<String, String> dataMap) {
        // 因为这个方法是在异步里面执行的，所以插入数据库需要同步线程
        synchronized(this) {
            AtomicReference<Integer> saveCount = new AtomicReference<>(0);
            dataMap.forEach((k, v) -> {
                saveCount.set(
                        saveCount.get() +
                                // 拼接 表名 sn_yyyyMMdd
                                deviceDataMapper.saveDeviceData(sn + "_" + simpleDateFormat.format(new Date()), sn, k, v)
                );
            });
            return saveCount.get();
        }
    }

    /**
     * @description: 获取设备最新数据
     * @author: Lm
     * @date: 2022/10/8 15:25
     * @param  sn, identifierMap
     * @return Map<String, String>  标识符对应的设备数据
     **/
    @Override
    public  List<DeviceIdentifierAndNameDataBo> getDeviceNewData(String sn) {

        // 1、先查询设备对应的物模型
        List<DeviceModel> deiceModelBySn = deviceModelService.getDeiceModelBySn(sn);

        // 如果该设备没有创建物模型
        if(deiceModelBySn.size() <= 0 ){
            // TODO 统一处理一下抛出异常
            return null;
        }

        // 2、将标识符转换为字符串 'tag1','tag2'.... 用于查询时序数据库的数据 这样做的话如果tag3没有的话就会查询出来多的一条数据
        String identifierStr= "";
        for (int i = 0; i <deiceModelBySn.size() ; i++) {
            // 拼接物模型的标识符
            if(i<deiceModelBySn.size()-1){
                identifierStr += "'" + deiceModelBySn.get(i).getIdentifier() + "'," ;
            }else{
                identifierStr += "'" + deiceModelBySn.get(i).getIdentifier() + "'" ;
            }
        }

        // 3、根据标识符字符串 sn 查询到对应的设备数据
        List<DeviceDataBo> deviceDataBos = deviceDataMapper.fineDeviceDatas(sn, identifierStr, deiceModelBySn.size());



        // 4、因为有些数据是没有上报到时序数据库里面的 但是查询时序数据库的时候查询会多出来数据 所以要去除重复的标识符
        deviceDataBos = deviceDataBos.stream().filter(
                distinctByKey(DeviceDataBo::getIdentifier)
        ).collect(Collectors.toList());

        // 拼接 标识符和数据
        Map<String, String> identifierToValMap = deviceDataBos.stream().collect(
                Collectors.toMap(DeviceDataBo::getIdentifier ,DeviceDataBo::getVal)
        );
        // 拼接 标识符和时间
        Map<String, Date> identifierToTsMap = deviceDataBos.stream().collect(
                Collectors.toMap(DeviceDataBo::getIdentifier ,DeviceDataBo::getTs)
        );


        // 返回标识符、名称和值
        List<DeviceIdentifierAndNameDataBo> deviceIdentifierAndNameDataList = new ArrayList<>();

        // 物模型数据作为循环 把数据都加载到list里面
        deiceModelBySn.stream().forEach(item->{
            DeviceIdentifierAndNameDataBo deviceIdentifierAndNameData = new DeviceIdentifierAndNameDataBo();
            deviceIdentifierAndNameData.setTs(identifierToTsMap.get(item.getIdentifier()));
            deviceIdentifierAndNameData.setIdentifier(item.getIdentifier());
            deviceIdentifierAndNameData.setName(item.getName());
            deviceIdentifierAndNameData.setVal(identifierToValMap.get(item.getIdentifier()));
            deviceIdentifierAndNameData.setUnit(item.getUnit());
            deviceIdentifierAndNameDataList.add(deviceIdentifierAndNameData);
        });


//        log.info("--->{}",deviceIdentifierAndNameDataList);
        return deviceIdentifierAndNameDataList;
    }

    /**
     * 分页查询
     * @param devicePageVo  vo
     * @return
     */
    @Override
    public Pager<DeviceBo> getDevicePager(DevicePageVo devicePageVo) {
        Pager<DeviceBo> pager = new Pager<>();
        // 先查询总条数
        pager.setTotalCount(deviceMapper.findDeviceCount());
        // 然后运算出总页数
        // 运算出总页数 总条数除以当前页条数 算出总页数
        if(pager.getTotalCount() % devicePageVo.getPageSize() ==0)
            //如果总数据量刚好被当前页面大小整除，那就可以直接相除算出页数
            pager.setTotalPageNum(pager.getTotalCount() / devicePageVo.getPageSize());
        else {
            //如果总数据量不能被当前页面大小整除，那就相除后再加上1，这样能保证
            //多余的数据页面会显示出来
            pager.setTotalPageNum(pager.getTotalCount() / devicePageVo.getPageSize()+1);
        }

        pager.setPageIndex(devicePageVo.getPageIndex());
        pager.setPageSize(devicePageVo.getPageSize());

        // 查询
        List<Device> db_deicePage = deviceMapper.findDeicePage(
                ((pager.getPageIndex() - 1) * pager.getPageSize()),
                pager.getPageSize(),
                devicePageVo.getKeyword()
        );

        // 返回Bo
        List<DeviceBo> deviceBoList = new ArrayList<>();
        // pojo 转bo
        db_deicePage.forEach(item->{
            DeviceBo deviceBo = new DeviceBo();
            BeanUtils.copyProperties(item,deviceBo);
            // 顺便查询设备是否在线
            deviceBo.setIsOnLine(RedisDeviceUtils.getDeviceIsOnLienBySn(deviceBo.getSn()));
            deviceBoList.add(deviceBo);
        });


        pager.setRecords(deviceBoList);
        return pager;
    }

    /**
     * 去重
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
