package com.lm.strategytask.V1;

import com.lm.admin.entity.dto.device.DeviceNewDataDto;
import com.lm.admin.entity.dto.devicestrategy.DeviceStrategyDto;
import com.lm.admin.entity.vo.device.DeviceCmdVo;
import com.lm.admin.mapper.tdengine.DeviceDataMapper;
import com.lm.admin.service.device.DeviceServiceImpl;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设备策略定时任务
 * @author Lm
 * @date 2022/11/5 21:17
 */
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
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
    @Scheduled(fixedRate = 500)
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
                // 插入查询到设备物模型的值 TODO 判断是否是数数字
                Double sn_tag_device_val = Double.valueOf(deviceNewDataDto.getData().get(sn_tag_split[1]));
                sn_tag_device_val = sn_tag_device_val == null ? 0:sn_tag_device_val;

                // 将sn_tag_str 和 设备物模型的值放进去
                context.put(sn_tag_str, sn_tag_device_val);
            }
            // 4、将策略表达式里面的{}去除掉
            dSitem.setExpStr(getDelStrSymbol(dSitem.getExpStr()));
            // 5、去除策略表达式的值 然后使用规则引擎判断处理表达式是否触发

            //下面五个参数意义分别是 表达式，上下文，errorList，是否缓存，是否输出日志
            try {
                Object result = expressRunner.execute(dSitem.getExpStr(), context, null, true, false);
                if((Boolean) result == true){
                    log.info("{}---->{}-----{}",dSitem,result,context);

//                    log.info("{}---->{}",dSitem,result);
                    // 执行添加任务逻辑
                    // 1、先获取任务
                    Object o = redisTemplate.opsForValue().get(CloudRedisKey.DeviceStrategyTaskPool + dSitem.getId().toString());
                    // 2、如果任务不存在就添加一个咯
                    if(o == null){
                        // 1、解析ret_data的数据 {dslkfhlahvhvdslhghwo2_led}:1:10 & {test_led1}:1:0

                        String ret_data_split[] = dSitem.getRetData().split("&");

                        // 创建任务
                        CmdJob addJob = new CmdJob();
                        addJob.setId(dSitem.getId());
                        // 2、将ret——data的数据转成Bean存入list
                        List<StrategyExecuteCmd> strategyExecuteCmdList = new ArrayList<>();
                        for (int i = 0; i <ret_data_split.length ; i++) {
                            // 将指令表达式转换出来 放到策略执行命令bean里面 dslkfhlahvhvdslhghwo2_led:1:10

                            // 这里将冒号分离出来 sn_标识符:数据:延迟时间
                            String snIn_v_t[] = ret_data_split[i].split(":");
                            // 这里将sn_标识符分离出来
                            String sn_In[] = snIn_v_t[0].split("_");


                            // 将解析好的ret_data存入
                            StrategyExecuteCmd strategyExecuteCmd = new StrategyExecuteCmd();


                            strategyExecuteCmd.setSn(sn_In[0]);
                            strategyExecuteCmd.setIdentifier(sn_In[1]);

                            strategyExecuteCmd.setData(snIn_v_t[1]);
                            // 设置执行时间 当前时间加上秒转毫秒
                            strategyExecuteCmd.setExecuteJobTime(Integer.valueOf( snIn_v_t[2]) * 1000);
                            strategyExecuteCmd.setExecuteJobTimestamp(System.currentTimeMillis() + (Integer.valueOf( snIn_v_t[2]) * 1000));
                            strategyExecuteCmdList.add(strategyExecuteCmd);
                        }
                        addJob.setStrategyExecuteCmdList(strategyExecuteCmdList);
                        // 将Job传入redis
                        redisTemplate.opsForValue().set(CloudRedisKey.DeviceStrategyTaskPool + dSitem.getId().toString(),addJob);

//                        log.info("策略id{}--->创建一个任务--->{}",addJob.getId(),addJob);

                    }
                    // 3、任务存在 就将执行事件加1秒
                    if(o != null){
//                        CmdJob cmdJob = (CmdJob) o;
//                        List<StrategyExecuteCmd> strategyExecuteCmdList = cmdJob.getStrategyExecuteCmdList();
//                        strategyExecuteCmdList.forEach(item->{
//
//                            if(item.getExecuteJobTime() > 0){
//                                // 加个1秒
//                                item.setExecuteJobTimestamp((item.getExecuteJobTimestamp()+1000));
//                            }
//                            else{
//                                // 如果原始没有设置执行时间就不加秒
//                            }
//
//                        });
//                        // 放回去
//                        cmdJob.setStrategyExecuteCmdList(strategyExecuteCmdList);
//                        redisTemplate.opsForValue().set(CloudRedisKey.DeviceStrategyTaskPool + dSitem.getId().toString(),cmdJob);
//                        log.info("策略id{}--->重复添加--->{}",cmdJob.getId(),cmdJob);
                    }
                }
                else{
                    // 不用 放空
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // redis任务线程
    @Scheduled(fixedRate = 100)
    private void JobTask(){
        Set<String> keys = redisTemplate.keys(CloudRedisKey.DeviceStrategyTaskPool + "*");

//        CmdJob cmdJob =(CmdJob) redisTemplate.opsForValue().get(CloudRedisKey.DeviceStrategyTaskPool + item);
//        Object  o1 =  redisTemplate.opsForValue().get(CloudRedisKey.DeviceStrategyTaskPool + 2+"");
//        int i = 1 ;
        keys.forEach(item->{
            CmdJob  cmdJob = (CmdJob) redisTemplate.opsForValue().get(item);
            // 获取设备命令列表
            List<StrategyExecuteCmd> strategyExecuteCmdList = cmdJob.getStrategyExecuteCmdList();
            // 开始发送命令
            strategyExecuteCmdList.forEach(sendCmd->{
                if(System.currentTimeMillis() >= sendCmd.getExecuteJobTimestamp()){
                    // 拼接数据 准备发送命令
                    DeviceCmdVo deviceCmdVo = new DeviceCmdVo(sendCmd.getSn(),sendCmd.getIdentifier(),sendCmd.getData());
                    // 查询redis设备数据
                    DeviceNewDataDto deviceNewDataDto =(DeviceNewDataDto) redisTemplate.opsForValue().get(CloudRedisKey.DeviceNewDataKey + sendCmd.getSn());
                    // 获取指定的值
                    String newDataVal = deviceNewDataDto.getData().get(deviceCmdVo.getIdentifier());
                    // 如果最新的数据和发送的数据一样就不重复发送了
                    if (deviceCmdVo.getData().equals(newDataVal) == true){

                    }else{
                        // 发送命令
                        DeviceCmdUtils.requestCmd(deviceCmdVo);
                        log.info("{}---发送指令",deviceCmdVo);
                    }
                    // TODO 加上判断时间
                    // 执行完删除就好了
                    redisTemplate.delete(item);
                }
                else{

                }
            });

        });
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