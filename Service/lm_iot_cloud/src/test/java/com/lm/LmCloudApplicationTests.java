package com.lm;

import com.lm.admin.entity.pojo.permission.Permission;
import com.lm.admin.mapper.mysql.permission.PermissionMapper;
import com.lm.admin.mapper.tdengine.DeviceDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = LmCloudApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
class LmCloudApplicationTests {

    @Autowired
    private DeviceDataMapper deviceDataMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    public void contextLoads() {
        final List<Permission> permission = permissionMapper.findPermission();
        log.info("{}",permission);
//        DeviceCmdData a = new DeviceCmdData();
//        a.setNts(DateTool.getThisDateStr());
//        a.setCmdID("1029351937889281232");
//        a.setData("aaa");
//        a.setStatus(true);
//        a.setRts(DateTool.getThisDateStr());
//
//        deviceDataMapper.saveDeiceCmd("sdg345tbdfbg3yg3q_20221012_cmd","sdg345tbdfbg3yg3q",a);
//        DeviceCmdData deviceCmdDataByCmdId = deviceDataMapper.getDeviceCmdDataByCmdId("1029351937889280000");
//        log.info("{}",deviceCmdDataByCmdId);
    }

}
