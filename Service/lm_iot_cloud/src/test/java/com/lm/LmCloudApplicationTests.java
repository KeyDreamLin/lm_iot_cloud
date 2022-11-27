package com.lm;

import com.lm.admin.entity.bo.device.DeviceSelectBo;
import com.lm.admin.entity.vo.device.DeviceSaveVo;
import com.lm.admin.mapper.mysql.device.BaseDeviceMapper;
import com.lm.admin.mapper.mysql.device.RoleAdminDeviceMapper;
import com.lm.admin.mapper.mysql.device.RoleUserDeviceMapper;
import com.lm.admin.mapper.mysql.permission.PermissionMapper;
import com.lm.admin.mapper.tdengine.DeviceDataMapper;
import com.lm.admin.service.device.DeviceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest(classes = LmCloudApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
class LmCloudApplicationTests {

    @Autowired
    private DeviceDataMapper deviceDataMapper;

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RoleUserDeviceMapper roleUserDeviceMapper;
    @Autowired
    private RoleAdminDeviceMapper roleAdminDeviceMapper;

    @Autowired
    private DeviceServiceImpl deviceService;



    @Test
    public void contextLoads() {
        DeviceSaveVo deviceSaveVo = new DeviceSaveVo();
        deviceSaveVo.setName("testCr");
        deviceSaveVo.setSn("testCr");
        deviceSaveVo.setSecretKey("testCr");

        deviceService.addDevice(deviceSaveVo);
//        testB = roleAdminDeviceMapper;
//        final Integer deviceCount = testB.findDeviceCount();
//        testB = roleUserDeviceMapper;
//        final List<DeviceSelectBo> deviceSnName1 = testB.findDeviceSnName();

        int i = 0 ;
    }

    /*
     * 是否为浮点数？double或float类型。
     * @param str 传入的字符串。
     * @return 是浮点数返回true,否则返回false。
     */
    public static boolean isDoubleOrFloat(String str) {
        Pattern pattern = Pattern.compile("^[-+]?(/d+(/./d*)?|/./d+)([eE]([-+]?([012]?/d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$");
        return pattern.matcher(str).matches();
    }

    /**
     * 是否为浮点数
     * @param str
     * @return
     */
    public static boolean isFloat(String str){
        return isDoubleOrFloat(str);
    }

    /**
     * 是否为double
     * @param str
     * @return
     */
    public static boolean isDouble(String str){
        return !isDoubleOrFloat(str);
    }
}
