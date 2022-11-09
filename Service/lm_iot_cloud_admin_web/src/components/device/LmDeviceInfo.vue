<template>
    <!-- 设备详细信息 -->
    <!-- before-close关闭方法前的回调 用于确认是否关闭 -->
    <div class="lm-dialog-main">
        <el-dialog
            v-model="isShowDialog" top="0vh" width="100%" :fullscreen="true"
            :before-close="close" center :lock-scroll="false" :show-close="false"
            :close-on-press-escape="false"
            >
            <!-- 自定义头部 -->
            <template #header="{ close, titleId, titleClass }">
                <div class="lm-dialog-header_box">
                    <div class="lm-dialog-header-left_box">
                        <el-button color="#a162f7" @click="close">
                            <el-icon class="mr-2">
                                <ArrowLeftBold />
                            </el-icon>
                            返回设备列表
                        </el-button>
                    </div>
                    <div class="lm-dialog-header-right_box">
                        <el-button color="#a162f7" @click="close">编辑设备</el-button>
                        <el-button color="#a162f7" @click="close">历史数据</el-button>
                        <el-button color="#a162f7" @click="close">历史命令</el-button>
                        <el-button color="#a162f7" @click="close">历史在线</el-button>
                        <el-button @click="close" type="danger">删除设备</el-button>
                    </div>
                </div>
           
            </template>
            <!-- 内容区 -->
            <div class="lm-dialog-body_full">
                <!-- 设备名称区头部 -->
                <div class="lm-dialog-body-header_box">
                    <!-- 设备名称 -->
                    <span class="lm-dialog_body-header-title">{{deviceData.name}}</span>
                      <!-- 设备状态 -->
                    <template v-if="deviceData.isOnLine==true">
                        <el-tag type="success">在线</el-tag>
                    </template>
                    <template v-else>
                        <el-tag type="danger">离线</el-tag>
                    </template>
                    <el-icon class="ml-3 text-xl text-black"><RefreshRight /></el-icon>
                </div>
                <!-- 设备信息区 -->
                <div class="lm-dialog-body-info_box">
                    <el-row :gutter="5" class="mt-3">
                        <el-col :span="8">
                            <div class="flex items-center">
                                <span class="lm-device-title">设备SN:</span>
                                <span class="lm-device-info">{{deviceData.sn}}</span>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div class="flex items-center">
                                <span class="lm-device-title">传输秘钥:</span>
                                <span class="lm-device-info">{{deviceData.sn}}</span>
                            </div>
                        </el-col>
                        <el-col :span="8">
                            <div class="flex items-center">
                                <span class="lm-device-title">上报记录数:</span>
                                <span class="lm-device-info">123123</span>
                            </div>
                        </el-col>
                    </el-row>

                    <el-row :gutter="12" class="mt-3 flex items-center">
                        <el-col :span="8">
                            <div class="flex items-center">
                                <span class="lm-device-title">所属分组:</span>
                                <span class="lm-device-info">aaaaa</span>
                            </div>
                        </el-col>
                        <el-col :span="16">
                            <div class="flex items-center">
                                <span class="lm-device-title">实时刷新:</span>
                                <el-switch :disabled="!deviceData.isOnLine" class="ml-1" @click="RealTimeSwitchEvent" :value="isRealTime" size="large"/>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                <!-- 搜索物模型区域 TODO 做定位吧，直接定位某个物模型 -->
                <!-- 物模型数据区 -->
                <div class="mt-3 lm-data_box">
                    <el-row :gutter="20" >
                        <template v-for="(deviceModel,index) in deviceModelData">
                            <el-col :xs="12" :sm="8" :md="6" :lg="4" :xl="2" class="mb-5">
                                <el-card class="">
                                    {{deviceModel}}
                                    <!-- 物模型卡片头部 -->
                                    <div class="lm-data-header_box">
                                        <!-- 左右布局 -->
                                        <div class="lm-data-header-name_box">
                                            <span>{{deviceModel.name}}</span>
                                        </div>
                                        <!-- 按钮 查看数据 编辑物模型 -->
                                        <div class="lm-data-header-but_box">
                                            <el-icon><Search /></el-icon>
                                            <el-icon class="ml-3"><Edit /></el-icon>
                                        </div>
                                    </div>
                                    <!-- 设备数据的值 -->
                                    <div class="lm-data-value_box">
                                        
                                        <template v-if="deviceModel.modelType==0">
                                            <span class="lm-data-value">
                                                {{deviceModel.val}}
                                            </span>
                                            <span class="lm-data-unit">{{deviceModel.unit}}</span>
                                        </template>
                                        <!-- 开关类型 并且设备上线 -->
                                        <template v-if="deviceModel.modelType==1">
                                            <span class="lm-data-value">
                                                {{
                                                deviceModel.val==1 
                                                ? 
                                                deviceModel.dataSpecs[1].name
                                                :
                                                deviceModel.dataSpecs[0].name
                                                }}
                                            </span>
                                            <template v-if="deviceData.isOnLine==true">
                                                <!-- 操作执行器 -->
                                                <el-switch
                                                    color="#b48bf7"
                                                    v-model="deviceModel.val"
                                                    size="large"
                                                    @click="SwitchCmdEvent(deviceModel)"
                                                    :active-value="deviceModel.dataSpecs[1].value"
                                                    :inactive-value="deviceModel.dataSpecs[0].value"
                                                />
                                            </template>
                                         
                                        </template>
                                    </div>
                                    <!-- 设备数据更新时间 -->
                                    <div class="lm-data-date_box">
                                        <span class="lm-data-date-value">
                                            {{deviceModel.ts}}
                                        </span>
                                    </div>
                                </el-card>
                            </el-col>
                        </template>
                    </el-row>
                </div>
            </div>
      
        </el-dialog>
    </div>
</template>

<script setup>
// import { LmMessageConfirm } from '@/utils';
import deviceService from '@/services/device/DeviceService';
import { onMounted, ref } from 'vue'
// 提供属性
const props = defineProps({

});
onMounted(()=>{
    console.log(props.sn);
});
// 控制设备详情关闭或者开启
const isShowDialog = ref(false);
// 设备管理传过来的设备数据
const deviceData = ref(null);
// 设备物模型数据
const deviceModelData = ref([]);
// 刷新数据定时器
let timerData = null;
// 刷新设备是否在线定时器
let timerIsOnline = null;
// 是否开启数据定时器
const isRealTime = ref(false);
// ref打开设备详情
const open = async (data) => {
    isShowDialog.value = true;
    deviceData.value = data;

    // 第一次打开dialog的时候获取一次数据
    // 获取一次设备数据
    await getDeviceData();
    // 获取设备物模型
    await getDeivceModel();
    // 获取设备物模型数据对应的值
    await getDeivceModelVal();     
    // 开启定时器
    timerIsOnline = setInterval(getIsOnline, 2 * 1000);
    
}
// 实时刷新数据开关事件
const RealTimeSwitchEvent = () => {
    if(deviceData.value.isOnLine == true){
        isRealTime.value = !isRealTime.value;
    }
    if(isRealTime.value == true){
        // 开启定时器
        timerData = setInterval(RealTimeDeivceModelVal, 5 * 1000);
    }
    else{
        // 销毁定时器
        clearInterval(timerData);
    }
}
// 获取设备信息
const getDeviceData = async () => {
    let server = await deviceService.listPage({keyword:deviceData.value.sn});
    deviceData.value = server.data.records[0];
}
// 获取设备物模型数据
const getDeivceModel = async () => {
    let responseModel = await  deviceService.deviceModel(deviceData.value);
    deviceModelData.value = responseModel.data;
    console.log("设备物模型----->",deviceModelData.value);
}
// 获取设备物模型数据对应的值
const getDeivceModelVal = async () => {
    let responseValData = await deviceService.getNewData(deviceData.value.sn);
    responseValData = responseValData.data;
    console.log("__________________________________",responseValData);
    // 通过物模型数据遍历最新的值，然后将最新的值插入到物模型数据中
    deviceModelData.value.forEach(modelData=>{
        // 处理执行器里面的dataSpecs 是个字符串不能直接用
        if(modelData.modelType == 1){
            if (typeof modelData.dataSpecs === "string") {
                modelData.dataSpecs = JSON.parse(modelData.dataSpecs);
            }
        }
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
    console.log("设备物模型最新数据----->",deviceModelData.value);
}
// 用于实时刷新数据
const RealTimeDeivceModelVal = async () => {
 
    if(isRealTime.value == true && deviceData.value.isOnLine == true){
        await getDeivceModelVal();
    }
}
// 获取设备是否在线
const getIsOnline = async () => {
    let temp = await deviceService.isOnLineBySn(deviceData.value.sn);
    deviceData.value.isOnLine = temp.data;
    console.log("__________________+)(*&YTGHJKas)");
    // 如果设备离线就关闭不允许实时刷新数据
    if(temp.data == false){
        isRealTime.value = false;
    }
}
// 操作当前物模型的执行器
const SwitchCmdEvent = async (thisModelData) => {
    let cmdData = {
        sn:deviceData.value.sn,
        identifier:thisModelData.identifier,
        data: thisModelData.val,
    }
    console.log("cmd",cmdData);
    let Cmdresponse = await deviceService.cmd(cmdData);
    console.log("cmd----------------",Cmdresponse);
    // console.log("________________________________________________LLLLLLLLLLLLLLLLLLL",thisModelData);
}
// 关闭设备详情
const close = () => {
    deviceModelData.value = [];
    isShowDialog.value = false;
    isRealTime.value = false;
    // 关闭窗口就把定时器销毁
    clearInterval(timerData);
    clearInterval(timerIsOnline);
}

// 抛出句柄 让父组件可以使用里面的方法 
defineExpose({
    open
})

</script>
<style scoped>
.dialog-footer button:first-child {
    margin-right: 10px;
}
.lm-dialog-main {
}
.lm-dialog-main:deep(.el-switch.is-checked .el-switch__core ){
    border-color: #a162f7;
    background: #a162f7;
}
.lm-dialog-main  :deep(.el-dialog__body){
    padding-top: 10px;
    padding-bottom: 10px ;
}
.lm-dialog-body {
    max-height: calc(100vh - 140px); 
       overflow-y: auto;
}
/* dialog头部盒子 */
.lm-dialog-header_box{
    display: flex;
    justify-content: space-between;
}
.lm-dialog-header_box:deep(.el-button ){
    color: #fff;
}
.lm-dialog-header-left_box{
}
.lm-dialog-header-right_box{
}
/* 盒子内容区主体头部 */
.lm-dialog-body-header_box{
  display: flex;
  align-items: center;
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
   width: 80px;
   display: block;
   background: #b48bf7;
   padding: 3px;
   margin-right: 3px;

   color: #fff;
   font-size: 12px;
   font-weight: 500;
   text-align: center;
   border-radius: 5px;
}
/* 设备信息数据 */
.lm-device-info{
   display: block;
   width: 260px;
   font-size: 14px;
   color: #000;
   white-space: nowrap;
   /* 溢出的文字会变成... */
   text-overflow: ellipsis;
   overflow: hidden;
}

/* 设备数据卡片盒子 */
.lm-data_box{
}
.lm-data_box:deep(.el-card__body){
    padding: 15px;
}
/* 设备数据卡片头部 */
.lm-data-header_box{
    display: flex;
    justify-content: space-between;
    align-items: center;
}
/* 设备数据左边盒子 展示数据名称 */
.lm-data-header-name_box{
    font-size: 16px;
    font-weight: 600;
}
/* 设备数据右边盒子 按键操作 */
.lm-data-header-but_box{
    display: flex;
    align-items: center;
    font-size: 20px;
    color: #a162f7;
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
/* 物模型数据更新时间的盒子 */
.lm-data-date_box{
    height: 21px;
}
/* 物模型数据更新时间的值 */
.lm-data-date-value{
    font-size: 14px;
    color: #999;
}
</style>