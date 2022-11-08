package com.lm.strategytask;

import com.lm.admin.entity.bo.device.DeviceDataTdBo;
import com.lm.admin.entity.bo.device.DeviceModelAndNewDataBo;
import com.lm.admin.entity.dto.device.DeviceNewDataDto;
import com.lm.admin.entity.dto.devicestrategy.DeviceStrategyDto;
import com.lm.admin.mapper.tdengine.DeviceDataMapper;
import com.lm.admin.service.device.DeviceServiceImpl;
import com.lm.admin.service.devicestrategy.DeviceStrategyServiceImpl;
import com.lm.common.redis.devicekey.CloudRedisKey;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设备策略定时任务
 * @author Lm
 * @date 2022/11/5 21:17
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class StrategyTask {
    // 设备策略服务类
    @Autowired
    private DeviceStrategyServiceImpl deviceStrategyService;
    // 设备数据mapp类
    @Autowired
    private DeviceDataMapper deviceDataMapper;

    @Autowired
    private DeviceServiceImpl deviceService;

    // 设备策略列表
    private List<DeviceStrategyDto> deviceStrategyDtos ;

    // 按照名字去匹配 不能用Autowired因为用类型匹配的
    @Resource(name = "fastjson2RedisTemplate")
    private RedisTemplate redisTemplate;  // 操作Redis

    // 动态规则引擎
    private static ExpressRunner expressRunner = new ExpressRunner();

    // 正则表达式
    Pattern pattern = Pattern.compile("\\{(.*?)}");
    // 保存匹配正则表达式的值
    Matcher matcher;
    // 每1秒轮询一次
    // 设备策略任务
    @Scheduled(cron = "*/1 * * * * ?")
    private void deviceStrategyTasks() {
        // 1、获取设备策略信息
        deviceStrategyDtos = deviceStrategyService.getAllDeviceStrategyDto();
        if(deviceStrategyDtos == null){
            return;
        }
        // 规则上下文
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        // 2、循环遍历所有的规则
        deviceStrategyDtos.forEach(dSitem->{
            // 3、使用正则表达式 获取策略表达式里面{}里面设备和标签的值并查询出最新数据
            matcher = pattern.matcher(dSitem.getExpStr());
            // 将正则表达式解析到的数据 传入上下文 ------ 处理策略表达式里面的数据
            while (matcher.find()) {
                // 将正则表达式解析出来的存入
                String sn_tag_str = matcher.group(1);
                // 分割字符串 将sn 和 tag 分割出来
                String[] sn_tag_split = sn_tag_str.split("_", 2);
                /// 查询设备物模型和最新数据 这里是直接获取redis
                DeviceNewDataDto deviceNewDataDto = (DeviceNewDataDto) redisTemplate.opsForValue().get(CloudRedisKey.DeviceNewDataKey + sn_tag_split[0]);

                // 直接退出整个方法就好了，反正都查询不到值
                if(deviceNewDataDto == null){return;}
                // 插入查询到设备物模型的值
                String sn_tag_device_val = deviceNewDataDto.getData().get(sn_tag_split[1]);
                sn_tag_device_val = sn_tag_device_val == null ? "":sn_tag_device_val;

                // 将sn_tag_str 和 设备物模型的值放进去
                context.put(sn_tag_str, sn_tag_device_val);
            }
            // 4、将策略表达式里面的{}去除掉
            dSitem.setExpStr(getDelStrSymbol(dSitem.getExpStr()));
            // 5、去除策略表达式的值 然后使用规则引擎判断处理表达式是否触发

            //下面五个参数意义分别是 表达式，上下文，errorList，是否缓存，是否输出日志
            try {
                Object result = expressRunner.execute(dSitem.getExpStr(), context, null, true, false);
                log.info("{}---->{}",dSitem,result);
                if((Boolean) result == true){
                    // 执行任务逻辑
                }
                else{
                    // 不用 放空
                }
//                log.warn("策略表达式 {}------------------------------------->",result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int o = 0 ;
        });
//        log.warn("-------------------------------------------------------------------{}",deviceStrategyDtos);
    }

    // 删除{}符号
    public String getDelStrSymbol(String exp_str){
        // 然后将数据库中查出来的规则字符串里面的{}删除
        exp_str = exp_str.replace("{","");
        exp_str = exp_str.replace("}","");

        return exp_str;
    }


}


// Cron表达式范例：
//
//每隔5秒执行一次：*/5 * * * * ?
//
//每隔1分钟执行一次：0 */1 * * * ?
//
//每天23点执行一次：0 0 23 * * ?
//
//每天凌晨1点执行一次：0 0 1 * * ?
//
//每月1号凌晨1点执行一次：0 0 1 1 * ?
//
//每月最后一天23点执行一次：0 0 23 L * ?
//
//每周星期天凌晨1点实行一次：0 0 1 ? * L
//
//在26分、29分、33分执行一次：0 26,29,33 * * * ?
//
//每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?