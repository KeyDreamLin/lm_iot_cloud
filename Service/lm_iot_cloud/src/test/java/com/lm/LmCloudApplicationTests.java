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
import java.util.regex.Pattern;

//@SpringBootTest(classes = LmCloudApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
class LmCloudApplicationTests {

    @Autowired
    private DeviceDataMapper deviceDataMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    public void contextLoads() {
        //{dslkfhlahvhvdslhghwo2_led}:1:10&{test_led1}:1:0
        String aaa = "{dslkfhlahvhvdslhghwo2_led}:1:10&{test_led1}:1:0";
        String bbb[] = aaa.split("&");

        double a = 1.12345678912345;
        // float 是8位有效数字，第7位数字将会四舍五入 所以正则表达式大于等于8的就是float
        float b = 1.12345678f;
        String c = "1.13e2";
        String cc = "1.1234567";

        System.out.println(Float.valueOf(c));

        System.out.println(a+",,,,,,,,"+b);
//        final List<Permission> permission = permissionMapper.findPermission();
//        log.info("{}",permission);
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
