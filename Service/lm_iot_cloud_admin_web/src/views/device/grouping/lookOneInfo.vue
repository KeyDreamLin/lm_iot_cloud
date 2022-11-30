<template>
    <!-- 设备分组数据 查看分组内设备的数据 只能查看和控制 -->
    <div class="lm-dialog-main">
            <div class="lm-dialog-header_box">
                <div class="lm-dialog-header-left_box mb-1">
                    <div class="lm_device_header_left_icon_box">
                        <el-icon class="mr-2">
                            <ArrowLeftBold />
                        </el-icon>  
                    </div>
                    <!-- 设备分组名称 -->
                    <span class="lm-dialog_body-header-title">
                        {{deviceGroupingInfo.name}}
                    </span>
                </div>
              
                <div class="lm-dialog-header-right_box">
                    <el-button color="#a162f7" @click="openUpdateDgEvent()">添加设备</el-button>
                </div>
        </div>

        <!-- 分组内设备数据展示 -->
        <div class="lm_device_data_body mt-2">
            <el-row :gutter="20">
                <template v-for="(deviceGrData,index) in deviceGroupingDeviceData">
                    <template v-for="(deviceData,index) in deviceGrData.data">
                        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="3"  class="mb-5">
                            <el-card class="lm-card_box">
                                <template #header>
                                    {{deviceGrData.name}}
                                </template>
                                
                                <!-- 物模型卡片头部 -->
                                <div class="lm-card-header_box">
                                    <!-- 左右布局 -->
                                    <div class="lm-card-header-name_box">
                                        <span>{{deviceData.name}}</span>
                                    </div>
                                    <!-- 按钮 查看数据 编辑物模型 -->
                                    <div class="lm-data-header-but_box">
                                        <!-- 查看数据按钮 -->
                                        <el-icon  class="ml-3 cursorPointer"><Search /></el-icon>
                                    </div>
                                </div>
                                <!-- 设备数据的值 -->
                                <div class="lm-card-value_box">
                                    <template v-if="deviceData.modelType==0">
                                        <span class="lm-card-value">
                                            <!-- 如果是bool的话 就获取dataSpece的值 0的别名和1的别名 如果不是bool就直接展示 -->
                                            {{
                                            deviceData.dataType=='bool' 
                                            ? 
                                            deviceData.dataSpecs[deviceData.val-0].name
                                            :
                                            deviceData.val
                                            }}
                                        </span>
                                        <span class="lm-card-unit">{{deviceData.unit}}</span>
                                    </template>
                                    <!-- 开关类型 并且设备上线 -->
                                    <template v-if="deviceData.modelType==1">
                                        <span class="lm-card-value">
                                            {{
                                            deviceData.val==1 
                                            ? 
                                            deviceData.dataSpecs[1].name
                                            :
                                            deviceData.dataSpecs[0].name
                                            }}
                                        </span>
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
                                <!-- 设备数据更新时间 -->
                                <div class="lm-card-date_box">
                                    <span class="lm-card-date-value">
                                        {{deviceData.ts}}
                                    </span>
                                </div>
                            </el-card>
                        </el-col>
                
                    </template>
                </template>
            </el-row>
        
        </div>
    </div>
    <lm-device-grouping-update-save-dialog :is-upadate="true" ref="dgDialogRef"></lm-device-grouping-update-save-dialog>
</template>
<script setup>
// 设备分组添加修改框
import lmDeviceGroupingUpdateSaveDialog from '@/components/device/LmDeviceGroupingUpdateSaveDialog.vue';
import deviceService from '@/services/device/DeviceService';
import deviceModelService from '@/services/devicemodel/DeviceModelService';
import { onActivated, onDeactivated, onMounted, ref } from 'vue';
import { computed } from '@vue/reactivity';
import { useRouter } from 'vue-router';
// 用于获取当前路由的状态和地址
import { useRoute } from 'vue-router';
import deviceGroupingService from '@/services/devicegrouping/DeviceGroupingService';
// 用于路由对象 对路由进行操作
const router = useRouter();
// 用于获取当前路由的状态和地址
const route = useRoute();
// 设备分组添加框ref
const dgDialogRef = ref(null);
// 分组信息
const deviceGroupingInfo = ref({});
// 分组内设备数据-里面包含数据-只有设备信息和设备数据 没有分组信息
const deviceGroupingDeviceData = ref([]);
// 路径中的设备分组id码
const pathDeviceGroupingId = computed(()=> route.query.id);
// 刷新数据定时器
let timerData = null;
// 刷新设备是否在线定时器
let timerIsOnline = null;
// 打开修改设备分组框
const openUpdateDgEvent = (()=>{
    // 传入设备分组id
    dgDialogRef.value.open(deviceGroupingInfo.value);
});
onActivated(async ()=>{
    // 根据分组id查询到分组信息
    let serverReqGroupingInfo = await deviceGroupingService.getDeviceGroupingById(pathDeviceGroupingId.value);
    deviceGroupingInfo.value = serverReqGroupingInfo.data;
    // 分组信息id 查询出分组内的设备信息
    let ServerReqDevices = await deviceService.deviceByGroupingId(deviceGroupingInfo.value.id);
    // 保存服务器查询到的分组设备信息
    deviceGroupingDeviceData.value = ServerReqDevices.data;
    console.log("分组设备信息-----------",deviceGroupingDeviceData.value);
    // 获取一次分组设备物模型数据
    await getDeviceGroupingOfModel();
    // 获取一次分组设备数据
    await getDeviceGroupingOfData(false);

    // 设备是否在线定时器
    timerIsOnline = setInterval(getIsOnline, 2 * 1000);
    // 数据刷新定时器
    timerData = setInterval(ReadTimeGetDeviceGroupingOfData, 3 * 1000);

});
// 查询分组设备内的物模型数据
const getDeviceGroupingOfModel =  async () => {
    deviceGroupingDeviceData.value.forEach (async (item) =>{
        let reqModel = await deviceModelService.getDeviceModelBySn({sn:item.sn});
        item.data = reqModel.data;
        console.log("------------",item);
    });
}

// 获取设备分组内的设备数据 
const getDeviceGroupingOfData = async (isRealTime) => {
    // 分别使用for循环去将deviceGroupingDeviceData的sn去查询该设备的最新数据
    // 第一次打开对话框的时候获取一次最新数据。但是在定时获取里面就需要判断设备在线的时候才获取最新数据 
    // 需要 是否处于定时任务中 如果是就判断设备在线才获取最新数据

    // 通过遍历分组信息中的设备数据 然后取出sn去查询设备的最新数据
    
    for (const index in deviceGroupingDeviceData.value) {
        const item = deviceGroupingDeviceData.value[index];
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
        console.log("开始查询name-->",item);
        // 获取该设备最新的值
        let responseValData = await deviceService.getNewData(item.sn);
        responseValData = responseValData.data;
        // 通过data里面的物模型数据去遍历
        item.data.forEach(modelData=>{
            // 处理执行器里面的dataSpecs 是个字符串不能直接用
            if (typeof modelData.dataSpecs == "string") {
                try {
                    modelData.dataSpecs = JSON.parse(modelData.dataSpecs);
                    // console.log("转");
                } catch (error) {
                    // 因为不是每个物模型都会有dataSpecs 所有没有json字符串的就会报错，抛出不管他
                }
            }
            console.log("333333333333333333",modelData);

            // 获取最新的值 
            responseValData.forEach(valModel=>{
                // 如果标识符是相等的话就把值赋进去
                if(modelData.identifier == valModel.identifier){
                    // 处理bool的数据 因为我们在java中将数据全部变成字符串来保存的，hhhh
                    if(modelData.dataType == "bool"){
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
    deviceGroupingDeviceData.value.forEach( async item=>{
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


onDeactivated(() => {
    console.log("关闭");
    console.log("关闭");
    console.log("关闭");
    // 关闭窗口就把定时器销毁
    clearInterval(timerData);
    clearInterval(timerIsOnline);
})
// 返回上一页
const looktgEvent = (()=>{
    // 关闭窗口就把定时器销毁
    router.go(-1)
});

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
    font-size: 20px;
    font-weight: 800;
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
.lm_device_header_left_icon_box{
    display: flex;
    align-items: center;
    cursor:pointer;
    margin-right: 5px;
}
.lm_device_header_left_icon_box :hover{
    color: #a162f7;
}
                 
.lm-dialog-header-left_box{
    color: #000;
    display: flex;
    justify-content: center;
    font-size: 20px;
    font-weight: 800;
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


/* 设备数据卡片头部 */
.lm-card-header_box{
    display: flex;
    justify-content: space-between;
    align-items: center;
}
/* 设备数据左边盒子 展示数据名称 */
.lm-card-header-name_box{
    font-size: 16px;
    font-weight: 600;
}
/* 设备数据右边盒子 按键操作 */
.lm-card-header-but_box{
    display: flex;
    align-items: center;
    font-size: 20px;
    color: #a162f7;
}
/* 物模型数据的盒子 */
.lm-card-value_box{
    height: 42px;
    margin-top: 5px;
    display: flex;
    align-items: center;
}

/* 物模型数据的值 */

.lm-card-value{
    font-size: 28px;
    margin-right: 5px;
}
/* 物模型数据的单位 */
.lm-card-unit{
    font-size: 18px;
}
/* 物模型数据更新时间的盒子 */
.lm-card-date_box{
    height: 21px;
}
/* 物模型数据更新时间的值 */
.lm-card-date-value{
    font-size: 14px;
    color: #999;
}

.lm_device_data_body:deep(.el-card__body){
    padding-top: 10px;
}
.lm_device_data_body:deep(.el-card__header){
    padding-bottom: 10px;
}
</style>