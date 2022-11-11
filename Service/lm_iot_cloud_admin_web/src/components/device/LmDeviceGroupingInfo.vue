<template>
    <!-- 设备分组数据 查看分组内设备的数据 只能查看和控制 -->
    <div class="lm-dialog-main">
        <el-dialog
            v-model="isShowDialog" top="0vh" width="100%" :fullscreen="true"
            :before-close="close" center :lock-scroll="false" :show-close="false"
            :close-on-press-escape="false">
             <!-- 自定义头部 -->
             <template #header="{ close, titleId, titleClass }">
                <div class="lm-dialog-header_box">
                    <div class="lm-dialog-header-left_box">
                        <el-button color="#a162f7" @click="close">
                            <el-icon class="mr-2">
                                <ArrowLeftBold />
                            </el-icon>
                            返回分组列表
                        </el-button>
                    </div>
                    <!-- 设备分组名称 -->
                    <div class="lm-dialog_body-header-title">
                    {{thisDeviceGroupingData.name}}
                    </div>
                    <div class="lm-dialog-header-right_box">
                        <el-button color="#a162f7" @click="close">编辑分组</el-button>
                        <el-button @click="close" type="danger">删除分组</el-button>
                    </div>
                </div>
            </template>

            <!-- 分组内设备数据展示 -->
            <div class="lm_device_data_body">
                <template v-for="(deviceGrData,index) in deviceGroupingData">
                    <!-- {{deviceGrData}} -->
                    <!-- 设备信息区 -->
                    <div class="lm_device_inif pl-1 ">
                        <div class="flex items-center">
                            <span class="lm-device-title">设备名称:</span>
                            <span class="lm-device-info">{{deviceGrData.name}}</span>
                            <!-- 设备状态 -->
                            <template v-if="deviceGrData.isOnLine==true">
                                <el-tag type="success">在线</el-tag>
                            </template>
                            <template v-else>
                                <el-tag type="danger">离线</el-tag>
                            </template>
                        </div>
                        <div class="flex items-center mt-2">
                            <span class="lm-device-title">设备SN:</span>
                            <span class="lm-device-info">{{deviceGrData.sn}}</span>
                        </div>
                    </div>
                    <!-- 数据展示区 -->
                    <div class="lm_device_data mt-3" >
                        <el-row :gutter="20" >
                            <template v-for="(deviceData,index) in deviceGrData.data">
                                <el-col :xs="12" :sm="8" :md="6" :lg="4" :xl="2" class="mb-5">
                                    <el-card class="">
                                        <!-- {{deviceData}} -->
                                        <div class="lm_data_tag">
                                            <span>{{deviceData.name}}</span>
                                        </div>
                                        <div class="lm-data-value_box">
                                            <!-- 是传感器的时候 -->
                                            <template v-if="deviceData.modelType==0">
                                                <span class="lm-data-value">
                                                {{deviceData.val}}
                                                </span>
                                                <span class="lm-data-unit">{{deviceData.unit}}</span>
                                            </template>
                                            <!-- 开关类型 并且设备上线 -->
                                            <template v-if="deviceData.modelType==1">
                                                <span class="lm-data-value">
                                                    {{
                                                    deviceData.val==1 
                                                    ? 
                                                    deviceData.dataSpecs[1].name
                                                    :
                                                    deviceData.dataSpecs[0].name
                                                    }}
                                                </span>
                                                <!-- 执行器专用的开关 -->
                                                <template v-if="deviceGrData.isOnLine==true">
                                                    <!-- 操作执行器 -->
                                                    <el-switch
                                                    color="#b48bf7"
                                                    v-model="deviceData.val"
                                                    size="large"
                                                    @click="SwitchCmdEvent(deviceData)"
                                                    :active-value="deviceData.dataSpecs[1].value"
                                                    :inactive-value="deviceData.dataSpecs[0].value"
                                                    />
                                                </template>
                                            </template>

                                        </div>
                                        <div class="lm-data-date_box">
                                            <span class="lm-data-date-value">
                                                {{deviceData.ts}}
                                            </span>
                                        </div>
                                    </el-card>
                                </el-col>
                            </template>
                        </el-row>
                    </div>
                </template>
               
            </div>
        </el-dialog>
    </div>
</template>
<script setup>
import deviceService from '@/services/device/DeviceService';
import { onMounted, ref } from 'vue';
const isShowDialog = ref(false);
// 当前对话框设备分组信息的数据
const thisDeviceGroupingData = ref(null);
// 分组内设备数据-里面包含数据
const deviceGroupingData = ref([]);
// 刷新数据定时器
let timerData = null;
// 刷新设备是否在线定时器
let timerIsOnline = null;
// 打开对话框方法
const open = async (data) => {
    // 传入分组信息数据
    let {...data_copy} = data;
    thisDeviceGroupingData.value = data_copy ;
    console.log(thisDeviceGroupingData.value);
    // 传入分组信息id 查询出分组内的设备信息
    let ServerReqDevices = await deviceService.deviceByGroupingId(thisDeviceGroupingData.value.id);
    // 保存服务器查询到的分组设备信息
    deviceGroupingData.value = ServerReqDevices.data;
    console.log("分组设备信息-----------",deviceGroupingData.value);
    // 获取一次分组设备物模型数据
    await getDeviceGroupingOfModel();
    // 获取一次分组设备数据
    await getDeviceGroupingOfData(false);

    // 设备是否在线定时器
    timerIsOnline = setInterval(getIsOnline, 2 * 1000);
    // 数据刷新定时器
    timerData = setInterval(ReadTimeGetDeviceGroupingOfData, 3 * 1000);

    isShowDialog.value = true;
}
// 查询分组设备内的物模型数据
const getDeviceGroupingOfModel =  async () => {
    deviceGroupingData.value.forEach (async (item) =>{
        let reqModel = await deviceService.deviceModel({sn:item.sn});
        item.data = reqModel.data;
        console.log("------------",item);
    });
}

// 获取设备分组内的设备数据 
const getDeviceGroupingOfData = async (isRealTime) => {
    // 分别使用for循环去将deviceGroupingData的sn去查询该设备的最新数据
    // 第一次打开对话框的时候获取一次最新数据。但是在定时获取里面就需要判断设备在线的时候才获取最新数据 
    // 需要 是否处于定时任务中 如果是就判断设备在线才获取最新数据

    // 通过遍历分组信息中的设备数据 然后取出sn去查询设备的最新数据
    
    for (const index in deviceGroupingData.value) {
        const item = deviceGroupingData.value[index];
        // 先判断是否处于定时任务中
        if(isRealTime ==  true){
            // 如果在定时任务中的话 需要判断一次设备是否在线
            if(item.isOnLine == false){
                // 如果这个分组中循环的这个设备不在线就跳出本次循环
                console.log(`当前设备不在线`,item.name);
                continue;
            }
        }
        // 不在定时任务中的就初始化的时候获取一次值
        console.log("开始查询name-->",item.name);
        // 获取该设备最新的值
        let responseValData = await deviceService.getNewData(item.sn);
        responseValData = responseValData.data;
        // 通过data里面的物模型数据去遍历
        item.data.forEach(modelData=>{
            // 处理执行器里面的dataSpecs 是个字符串不能直接用
            if(modelData.modelType == 1){
                if (typeof modelData.dataSpecs === "string") {
                    modelData.dataSpecs = JSON.parse(modelData.dataSpecs);
                }
                console.log("22222222222222",modelData);
            }
            console.log("333333333333333333",modelData);

            // 获取最新的值 
            responseValData.forEach(valModel=>{
                // 如果标识符是相等的话就把值赋进去
                if(modelData.identifier == valModel.identifier){
                    // 处理bool的数据 因为我们在java中将数据全部变成字符串来保存的，hhhh
                    if(modelData.dataType === "bool"){
                        // 将字符串转int
                        modelData.val = valModel.val - 0;
                    }
                    else {
                        modelData.val = valModel.val != '' ?  valModel.val : "N/A"  ;
                    }
                    modelData.ts = valModel.ts != null ?  valModel.ts : "— —"  ;
                }
               
            });
        });
    }
}
// 定时读取分组设备数据 函数
const ReadTimeGetDeviceGroupingOfData = async () => {
   await getDeviceGroupingOfData(true);
}

// 获取设备是否在线
const getIsOnline = async () => {
    deviceGroupingData.value.forEach( async item=>{
        let temp = await deviceService.isOnLineBySn(item.sn);
        item.isOnLine = temp.data;
    })
}

// 操作当前物模型的执行器  看一下给开关做个限制操作的玩意 不行连续点
const SwitchCmdEvent = async (thisModelData) => {
    let cmdData = {
        sn:thisModelData.sn,
        identifier:thisModelData.identifier,
        // 将bool转为数字
        data: thisModelData.val+0,
    }
    console.log("cmd",cmdData);
    let Cmdresponse = await deviceService.cmd(cmdData);
    console.log("cmd----------------",Cmdresponse);
}
const close = (data) => {
    console.log("关闭");
    thisDeviceGroupingData.value.id = -1;
    thisDeviceGroupingData.value.name = "";
    thisDeviceGroupingData.value.createTime = "";
    thisDeviceGroupingData.value.updateTime = "";
    // 关闭窗口就把定时器销毁
    clearInterval(timerData);
    clearInterval(timerIsOnline);
    isShowDialog.value = false;
}
// 抛出句柄 让父组件可以使用里面的方法 
defineExpose({
    open
})

</script>
<style scoped>
.lm-dialog-main {
}
.lm-dialog-main:deep(.el-switch.is-checked .el-switch__core ){
    border-color: #a162f7;
    background: #a162f7;
}
.lm-dialog-main  :deep(.el-dialog__header){
    padding-top: 10px;
    margin: 0;
}
.lm-dialog-main  :deep(.el-dialog__body){
    padding-top: 10px;
    padding-bottom: 10px ;
}
/* 盒子内容区主体头部标题 */
.lm-dialog_body-header-title{
    color: #373d41;
    font-size: 28px;
    font-weight: 700;
    margin-right: 10px;
}
/* 设备信息标题 */
.lm-device-title{
   width: 100px;
   display: block;
   background: #b48bf7;
   padding: 3px;
   margin-right: 3px;

   color: #fff;
   font-size: 16px;
   font-weight: 500;
   text-align: center;
   border-radius: 5px;
}


/* 设备信息数据 */
.lm-device-info{
   display: block;
   margin-left: 3px;
   margin-right: 5px;
   font-size: 19px;
   color: #000;
   white-space: nowrap;
   /* 溢出的文字会变成... */
   text-overflow: ellipsis;
   overflow: hidden;
}
/* dialog头部盒子 */
.lm-dialog-header_box{
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.lm-dialog-header_box:deep(.el-button ){
    color: #fff;
}
/* 设备数据卡片盒子 */
.lm_data_tag{
    font-size: 16px;
    font-weight: 600;
}

/* 物模型数据的盒子 */
.lm-data-value_box{
    height: 42px;
    margin-top: 5px;
    display: flex;
    align-items: center;
}

/* 物模型数据的值 */

.lm-data-value{
    font-size: 28px;
    margin-right: 5px;
}
/* 物模型数据的单位 */
.lm-data-unit{
    font-size: 18px;
}
</style>