package com.lm.strategytask.V2;

import com.lm.admin.entity.dto.device.DeviceNewDataDto;
import com.lm.admin.entity.dto.devicestrategy.DeviceStrategyDto;
import com.lm.admin.entity.vo.device.DeviceCmdVo;
import com.lm.admin.service.devicestrategy.DeviceStrategyServiceImpl;
import com.lm.cloud.tcp.service.utils.DeviceCmdUtils;
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
import java.util.Set;
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

    // TODO 后续加上Zset有序排序 作为轮询延迟的任务池


    // 每1秒轮询一次
    // 设备策略任务
    @Scheduled(fixedRate = 1000)
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
            matcher = pattern.matcher(dSitem.getTriggerStr());
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
                // 插入查询到设备物模型的值 TODO 判断是否是数数字
                Double sn_tag_device_val = Double.valueOf(deviceNewDataDto.getData().get(sn_tag_split[1]));
                sn_tag_device_val = sn_tag_device_val == null ? 0:sn_tag_device_val;

                // 将sn_tag_str 和 设备物模型的值放进去
                context.put(sn_tag_str, sn_tag_device_val);
            }
            // 4、将策略表达式里面的{}去除掉
            dSitem.setTriggerStr(getDelStrSymbol(dSitem.getTriggerStr()));
            // 5、去除策略表达式的值 然后使用规则引擎判断处理表达式是否触发

            //下面五个参数意义分别是 表达式，上下文，errorList，是否缓存，是否输出日志
            try {
                Object result = expressRunner.execute(dSitem.getTriggerStr(), context, null, false, false);
                if((Boolean) result == true){
                    //  需要处理多个命令使用 & 分割  示例 --> sdg345ghdgh345gweqer23_led : 1 : 0 & test_tag1 : 1 : 0
                    String[] ret_data_split =  dSitem.getActionStr().split("&");
                    // 循环多个命令
                    for (String retitem : ret_data_split){
                        // 示例 --> sdg345ghdgh345gweqer23_led : 1 : 0 做:分割
                        String[] sn_identifier_time = retitem.split(":");
                        // 然后开始拆解 sn_identifier
                        String sn_identifier[] = sn_identifier_time[0].split("_");

                        // 判断是否存在任务
                        Object o = redisTemplate.opsForValue().get(CloudRedisKey.DeviceStrategyTaskPool + sn_identifier_time[0]);
                        if(o == null){
                            StrategyExecuteCmd strategyExecuteCmd = new StrategyExecuteCmd();
                            // 拿sn_identifier作为任务名称
                            strategyExecuteCmd.setTaskName(sn_identifier_time[0]);
                            strategyExecuteCmd.setData(sn_identifier_time[1]);
                            // 传入延时的时间
                            strategyExecuteCmd.setExecuteJobTime( (Integer.valueOf(sn_identifier_time[2]) * 1000) );
                            // 传入当前时间 + 延时的时间
                            strategyExecuteCmd.setExecuteJobTimestamp( System.currentTimeMillis() + (Integer.valueOf(sn_identifier_time[2]) * 1000) );

                            // 传入 sn 和 identifier
                            strategyExecuteCmd.setSn(sn_identifier[0]);
                            strategyExecuteCmd.setIdentifier(sn_identifier[1]);
                            // 放入redis任务池
                            redisTemplate.opsForValue().set(CloudRedisKey.DeviceStrategyTaskPool + strategyExecuteCmd.getTaskName(), strategyExecuteCmd);
                            // log.info("{}--被放入任务池", strategyExecuteCmd);
                        }
                        // 如果存在任务
                        else{
                            StrategyExecuteCmd strategyExecuteCmd = (StrategyExecuteCmd) o;
                            // 如果这个规则的发送命令是没有延时的话 就将有延时命令的任务的延时时间覆盖
                            // 如果规则中的ret_data是没有设置延时的话 那就将有延时的ret_data覆盖掉
                            if(sn_identifier_time[0].equals(strategyExecuteCmd.getTaskName()) && Integer.valueOf(sn_identifier_time[2]) <= 0 ){
                                strategyExecuteCmd.setData(sn_identifier_time[1]);
                                // 传入延时的时间
                                strategyExecuteCmd.setExecuteJobTime( Integer.valueOf(sn_identifier_time[2]) );
                                // 传入当前时间 + 延时的时间
                                strategyExecuteCmd.setExecuteJobTimestamp( System.currentTimeMillis()  );

                                // 传入 sn 和 identifier
                                strategyExecuteCmd.setSn(sn_identifier[0]);
                                strategyExecuteCmd.setIdentifier(sn_identifier[1]);
                                // 放入redis任务池
                                redisTemplate.opsForValue().set(CloudRedisKey.DeviceStrategyTaskPool + strategyExecuteCmd.getTaskName(), strategyExecuteCmd);
                            }
                        }
                    }


                }
                else{
                    // 不用 放空
                }
            } catch (Exception e) {
//                log.error(e.getMessage());
            }
        });
    }

    // redis任务线程
    @Scheduled(fixedRate = 300)
    private void JobTask(){
        Set<String> keys = redisTemplate.keys(CloudRedisKey.DeviceStrategyTaskPool + "*");
        for (String itemKey : keys) {
            Object o = redisTemplate.opsForValue().get(itemKey);
            if(o == null){ continue; }
            StrategyExecuteCmd strategyExecuteCmd = (StrategyExecuteCmd) o;
            // 延时触发
            if(System.currentTimeMillis() >= strategyExecuteCmd.getExecuteJobTimestamp()) {
                // 拼接数据 准备发送命令
                DeviceCmdVo deviceCmdVo = new DeviceCmdVo(strategyExecuteCmd.getSn(), strategyExecuteCmd.getIdentifier(), strategyExecuteCmd.getData());
                // 查询redis设备数据
                DeviceNewDataDto deviceNewDataDto = (DeviceNewDataDto) redisTemplate.opsForValue().get(CloudRedisKey.DeviceNewDataKey + strategyExecuteCmd.getSn());
                // 如果查询不到设备数据 那就不管
                if (deviceNewDataDto == null) {
                    continue;
                }
                // 获取指定的值
                String newDataVal = deviceNewDataDto.getData().get(deviceCmdVo.getIdentifier());
                // 如果查询不到值 那就不管
                if (newDataVal == null) {
                    continue;
                }
                // 如果最新的数据和发送的数据一样就不重复发送了
                if (deviceCmdVo.getData().equals(newDataVal) == true) {

                } else {
                    // 发送命令
                    DeviceCmdUtils.requestCmd(deviceCmdVo);
                    log.info("{}---发送指令", deviceCmdVo);
                }
                // 执行命令完删除就好了
                redisTemplate.delete(itemKey);
            }
        }
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