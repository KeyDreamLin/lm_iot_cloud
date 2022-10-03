package com.lm;

import com.lm.admin.entity.pojo.devicemodel.DeviceModelData;
import com.lm.admin.mapper.mysql.device.DeviceModelDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = LmCloudApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
class LmCloudApplicationTests {
    //tag1
    //tag2
    //tag3
    //tag4
    @Autowired
    private DeviceModelDataMapper deviceModelDataMapper;
    @Test
    public void contextLoads() {
        List<DeviceModelData> deviceModelList = deviceModelDataMapper.findDeviceModelBySn("sdg345tbdfbg3yg3q");
        List<String> bsf = new ArrayList<>();
        deviceModelList.forEach(item->{
            bsf.add(item.getIdentifier());
        });
        log.info("{}",bsf);
    }

}
