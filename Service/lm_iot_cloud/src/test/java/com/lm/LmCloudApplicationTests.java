package com.lm;

import com.alibaba.fastjson2.JSON;
import com.lm.admin.entity.bo.device.DeviceCmdBo;
import com.lm.admin.entity.dto.device.DeviceDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(classes = LmCloudApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
class LmCloudApplicationTests {
    @Test
    public void contextLoads() {
        DeviceCmdBo deviceDto = new DeviceCmdBo();
        Map<String,Object> a = new HashMap<>();
        a.put("deng",1);
        deviceDto.setData(a);
        log.info("{}", JSON.toJSON(deviceDto));
    }

}
