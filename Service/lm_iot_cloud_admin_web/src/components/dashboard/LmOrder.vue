<template>
    <el-row :gutter="30" class="lm-order_box">
         <!-- 设备总数 -->
         <el-col :span="6">
            <el-card class="lm-card_box">
                <div class="lm-card-title1_box">
                    <span>设备总数</span>
                </div>
                <div class="lm-card-title2_box">
                    <div class="lm-card-title-wrapper_box">
                        <lm-num :value="deviceCount" class="lm-card-num_box"></lm-num>
                        <span>台</span>
                    </div>
                </div>
                <div class="lm-card-title3_box">
                    <div class="lm-card-title-wrapper_box">
                        <span>今天上线</span>
                        <lm-num :value="deviceUpCount" class="lm-card-num_box"></lm-num>
                        <span>台</span>
                    </div>
                </div>
            </el-card>  
        </el-col>

        <!-- 传感器总数 -->
        <el-col :span="6">
            <el-card class="lm-card_box">
                <div class="lm-card-title1_box">
                    <span>传感器总数</span>
                </div>
                <div class="lm-card-title2_box">
                    <div class="lm-card-title-wrapper_box">
                        <lm-num :value="deviceModelCount" class="lm-card-num_box"></lm-num>
                        <span>个</span>
                    </div>
                </div>
                <div class="lm-card-title3_box">
                    <div class="lm-card-title-wrapper_box">
                        <span>今日新增</span>
                        <lm-num :value="thisDayDeviceModelCount" class="lm-card-num_box"></lm-num>
                        <span>个</span>
                    </div>
                </div>
            </el-card>  
        </el-col>

        <!-- 传感器数据总数 -->
        <el-col :span="6">
            <el-card class="lm-card_box">
                <div class="lm-card-title1_box">
                    <span>传感器数据总数</span>
                </div>
                <div class="lm-card-title2_box">
                    <div class="lm-card-title-wrapper_box">
                        <lm-num :value="deviceUpDataCount" class="lm-card-num_box"></lm-num>
                        <span>条</span>
                    </div>
                </div>
                <div class="lm-card-title3_box">
                    <div class="lm-card-title-wrapper_box">
                        <span>今日上报</span>
                        <lm-num :value="thisDayDeviceUpDataCount" class="lm-card-num_box"></lm-num>
                        <span>条</span>
                    </div>
                </div>
            </el-card>  
        </el-col>

        <!-- 策略总数 -->
        <el-col :span="6">
            <el-card class="lm-card_box">
                <div class="lm-card-title1_box">
                    <span>策略总数</span>
                </div>
                <div class="lm-card-title2_box">
                    <div class="lm-card-title-wrapper_box">
                        <lm-num :value="deviceStrategyCount" class="lm-card-num_box"></lm-num>
                        <span>个</span>
                    </div>
                </div>
                <div class="lm-card-title3_box">
                    <div class="lm-card-title-wrapper_box">
                        <span>已启用</span>
                        <lm-num :value="openStrategyCount" class="lm-card-num_box"></lm-num>
                        <span>个</span>
                    </div>
                </div>
            </el-card>  
        </el-col>

    </el-row>
</template>

<script setup>
import deviceService from '@/services/device/DeviceService';
import deviceModelService from '@/services/devicemodel/DeviceModelService';
import deviceStrategyService from '@/services/devicestrategy/DeviceStrategyService';
import { onActivated, ref } from 'vue';
// 平台有多少台设备
const deviceCount = ref(0);
// 平台设备在线数
const deviceUpCount = ref(0);

// 平台设备物模型总数
const deviceModelCount = ref(0);
// 平台今天新增物模型总数
const thisDayDeviceModelCount = ref(0);

// 平台设备数据上报总数
const deviceUpDataCount = ref(0);
// 平台今天设备上报数据总数
const thisDayDeviceUpDataCount = ref(0);

// 平台设备策略总数
const deviceStrategyCount = ref(0);
// 平台策略启用的数量
const openStrategyCount = ref(0);



onActivated(async ()=>{
    // 平台有多少台设备
    let deviceCountResponse = await deviceService.getDeviceCount();
    // 平台设备在线数
    let deviceUpCountResponse = await deviceService.getDeviceUpCount();

    // 平台设备物模型总数
    let deviceModelCountResponse = await deviceModelService.getDeviceModelAllCount();
    // 平台今天添加了多少物模型
    let thisDayDeviceModelCountResponse = await deviceModelService.getThisDayNewDeviceModelCount();


    // 平台全部设备数据上报数
    let deviceUpDataCountResponse = await deviceService.getDeviceDataUpCount();
    // 平台全部设备当天上报数
    let thisDayDeviceUpDatatResponse = await deviceService.getThisDayDeviceDataUpCount();


    // 平台设备策略总数
    let deviceStrategyCountResponse = await deviceStrategyService.getAllCount();
    // 平台策略启用的数量
    let openDeviceStrategyCountResponse = await deviceStrategyService.getOpenCount();




    
    deviceCount.value = deviceCountResponse.data;
    deviceUpCount.value = deviceUpCountResponse.data;

    deviceModelCount.value = deviceModelCountResponse.data;
    thisDayDeviceModelCount.value = thisDayDeviceModelCountResponse.data;

    deviceUpDataCount.value = deviceUpDataCountResponse.data;
    thisDayDeviceUpDataCount.value = thisDayDeviceUpDatatResponse.data;


    deviceStrategyCount.value = deviceStrategyCountResponse.data;
    openStrategyCount.value = openDeviceStrategyCountResponse.data;

});
</script>

<style scoped>

/* 去除卡片的阴影、边框 */
.lm-order_box :deep(.el-card){
    box-shadow: none;
    /* 圆角 */
    border-radius: 15px;
    border :0;
}
.lm-order_box :deep(.el-card__body){
    padding-top: 15px;
    padding-left: 15px;
}
.lm-card_box{
  
}
.lm-card-title1_box{
    display: block;
    font-size: 15px;
    font-weight: 700;
}
.lm-card-title2_box{
    display: flex;
    justify-content: center;
    align-items: center;
}
.lm-card-title3_box{
   display: flex;
    justify-content:right;
}
.lm-card-title3-wrapper_box{
    
}
/* 数字 */
.lm-card-num_box{
    color:#a162f7;
    padding: 0 5px;
    font-size: 25px;
    font-weight: 700;
}
</style>