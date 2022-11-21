<template>
    <!-- 设备详情 -->
     <div class="lm_device_box">
        <!-- 设备信息头部 -->
        <div class="lm_device_header_box">
            <!-- 头部左边 -->
            <div class="lm_device_header_left_box">
                <div class="lm_device_header_left_icon_box" @click="looktgEvent">
                    <el-icon><ArrowLeftBold /></el-icon>
                </div>
                <div class="flex items-center">
                    <span>{{deviceInfoData.name}}</span>
                    <div class="ml-2 flex items-center">
                        <!-- 设备状态 -->
                        <template v-if="deviceInfoData.isOnLine==true">
                            <el-tag type="success">在线</el-tag>
                        </template>
                        <template v-else>
                                <el-tag type="danger">离线</el-tag>
                        </template>
                    </div>
                </div>
            </div>
            <!-- 头部右边 -->
            <div class="lm_device_header_right_box">
                <el-button type="info" @click="looktgEvent">编辑设备</el-button>
                <el-button type="info" @click="looktgEvent">历史数据</el-button>
                <el-button type="info" @click="looktgEvent">历史命令</el-button>
                <el-button type="info" @click="looktgEvent">历史在线</el-button>
            </div>
        </div>
        <!-- 设备信息数据展示区域 -->
        <div class="lm_device_data_box">
            <!-- 设备信息区 -->
            <div class="lm_device_data_info_box">
                <el-clo :gutter="5">
                    <el-row :span="24">
                        <div class="info_box">
                            <span class="info_tag">SN:</span>
                            <span class="info_val">{{deviceInfoData.sn}}</span>
                        </div>
                        <div class="info_box">
                            <span class="info_tag">secretKey:</span>
                            <span class="info_val">{{deviceInfoData.secretKey}}</span>
                        </div>
                        <div class="info_box">
                            <span class="info_tag">上报记录数:</span>
                            <span class="info_val">45896524</span>
                        </div>
                        <div class="info_box">
                            <span class="info_tag">所属分组:</span>
                            <span class="info_val">小马的家小马的家</span>
                        </div>
                        <div class="info_box">
                            <span class="info_tag ml-2">实时刷新:</span>
                            <el-switch  @click="RealTimeSwitchEvent"  v-model="switchReadDeviceNewValTime" size="default"/>
                        </div>
                    </el-row>
                </el-clo>
            </div>
            <!-- 设备数据展示区域 -->
            <div class="lm_device_data_ValData_box">
                <el-row :gutter="20" >
                    <!-- {{deviceValDatas}} -->
                    <template v-for="(deviceValData,index) in deviceValDatas">
                        <el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="3"  class="mb-5">
                            <el-card class="lm-card_box">
                                    <!-- 物模型卡片头部 -->
                                    <div class="lm-card-header_box">
                                        <!-- 左右布局 -->
                                        <div class="lm-card-header-name_box">
                                            <span>{{deviceValData.name}}</span>
                                        </div>
                                        <!-- 按钮 查看数据 编辑物模型 -->
                                        <div class="lm-data-header-but_box">
                                            <el-icon><Search /></el-icon>
                                            <el-icon class="ml-3"><Edit /></el-icon>
                                        </div>
                                    </div>
                                    <!-- 设备数据的值 -->
                                    <div class="lm-card-value_box">
                                        <template v-if="deviceValData.modelType==0">
                                            <span class="lm-card-value">
                                                {{deviceValData.val}}
                                            </span>
                                            <span class="lm-card-unit">{{deviceValData.unit}}</span>
                                        </template>
                                        <!-- 开关类型 并且设备上线 -->
                                        <template v-if="deviceValData.modelType==1">
                                            <span class="lm-card-value">
                                                {{
                                                deviceValData.val==1 
                                                ? 
                                                deviceValData.dataSpecs[1].name
                                                :
                                                deviceValData.dataSpecs[0].name
                                                }}
                                            </span>
                                            <template v-if="deviceInfoData.isOnLine==true">
                                                <!-- 操作执行器 -->
                                                <el-switch
                                                    color="#b48bf7"
                                                    v-model="deviceValData.val"
                                                    size="large"
                                                    @click="SwitchCmdEvent(deviceValData)"
                                                    :active-value="deviceValData.dataSpecs[1].value"
                                                    :inactive-value="deviceValData.dataSpecs[0].value"
                                                />
                                            </template>
                                         
                                        </template>
                                    </div>
                                    <!-- 设备数据更新时间 -->
                                    <div class="lm-card-date_box">
                                        <span class="lm-card-date-value">
                                            {{deviceValData.ts}}
                                        </span>
                                    </div>
                                </el-card>
                        </el-col>
                    </template>
                </el-row>
            </div>
        </div>
    </div>
</template>
<script setup>
// 用于路由对象 对路由进行操作
import deviceService from '@/services/device/DeviceService';
import { computed } from '@vue/reactivity';
import { onActivated, onBeforeMount, onBeforeUnmount, onDeactivated, onMounted, onUnmounted, ref } from 'vue';
import { useRouter } from 'vue-router';
// 用于获取当前路由的状态和地址
import { useRoute } from 'vue-router';

// 用于路由对象 对路由进行操作
const router = useRouter();
// 用于获取当前路由的状态和地址
const route = useRoute();
// 服务器-设备信息
const deviceInfoData = ref({});
// 服务器-设备数据的值
const deviceValDatas = ref([]);
// 路径中的设备sn码
const pathDeviceSn = computed(()=> route.query.sn);
// 获取设备信息
const getDeviceInfo = (async ()=>{
    let responseDeviceInfo = await deviceService.listPage({"keyword":pathDeviceSn.value});
    deviceInfoData.value = responseDeviceInfo.data.records[0];
    console.log("device_lookOneInfo-getDeviceInfo---->",deviceInfoData.value);
});

// 获取最新的数据--设备最新数据就包含物模型的数据了
const getDeviceNewDataVal = (async ()=>{
    // 服务器-回调最新\历史数据
    let responseDeviceNewDataVal = await deviceService.getNewData(pathDeviceSn.value);
    deviceValDatas.value = responseDeviceNewDataVal.data;
    console.log("device_lookOneInfo-getDeviceNewDataVal---->",responseDeviceNewDataVal);
    // 处理数组 设备数据----
    // 遍历设备数据
    deviceValDatas.value.forEach(valItem => {
        // 首先处理dataSpecs 字符串转为json
        if (typeof valItem.dataSpecs === "string" && valItem.dataSpecs.length>0 ) {
            valItem.dataSpecs = JSON.parse(valItem.dataSpecs);
        }

        // 然后在处理val 和 ts 的空数据
        // 处理bool的数据 因为我们在java中将数据全部变成字符串来保存的，hhhh
        if(valItem.dataType === "bool"){
            // 将字符串转int
            valItem.val = valItem.val - 0;
        }
        else {
            valItem.val = valItem.val != '' ?  valItem.val : "N/A"  ;
        }
        valItem.ts = valItem.ts != null ?  valItem.ts : "— —"  ;

    });
    console.log("device_lookOneInfo-处理后的设备数据---->",deviceValDatas);

});
// 返回上一页
const looktgEvent = (()=>{
    router.go(-1)
});

// 获取设备是否在线
const getIsOnline = async () => {
    let temp = await deviceService.isOnLineBySn(deviceInfoData.value.sn);
    deviceInfoData.value.isOnLine = temp.data;
    // 如果设备离线就关闭不允许实时刷新数据
    if(temp.data == false){
        switchReadDeviceNewValTime.value = false;
        clearInterval(readDeviceNewValTime);
    }
}
// switch 控件切换是否实时属性设备数据
const RealTimeSwitchEvent = (()=>{
    if(switchReadDeviceNewValTime.value == true){
        readDeviceNewValTime = setInterval(getDeviceNewDataVal, 3 * 1000);
    }
    else{
        clearInterval(readDeviceNewValTime);
    }
});

// switch 控件 操作当前物模型的执行器
const SwitchCmdEvent = async (thisModelData) => {
    let cmdData = {
        sn:deviceInfoData.value.sn,
        identifier:thisModelData.identifier,
        data: thisModelData.val,
    }
    let Cmdresponse = await deviceService.cmd(cmdData);
    console.log("cmd----------------",Cmdresponse);
}
// 读取设备是否在线定时器
let readDeviceIsOnLineTime ;
// 读取最新的设备数据定时器
let readDeviceNewValTime ;
// 切换定时器状态 开启和停止 设备数据定时器
const switchReadDeviceNewValTime = ref(false);
onActivated(()=>{
    // 获取一次设备信息
    getDeviceInfo();
    // 获取设备的数据的值
    getDeviceNewDataVal();
    readDeviceIsOnLineTime = setInterval(getIsOnline, 1 * 1000);
    console.log("ddddddddddddddddddddddddddddd");
});
onDeactivated(() => {
    switchReadDeviceNewValTime.value = false;
    clearInterval(readDeviceIsOnLineTime);
    clearInterval(readDeviceNewValTime);
    // 服务器-设备信息
    deviceInfoData.value = [];
    // 服务器-设备数据的值
    deviceValDatas.value = [];
    
})

</script>

<style scoped>
/* 设备头部盒子 */
.lm_device_header_box{
    display: flex;
    justify-content: center;
}
/* 设备头部左边 */
.lm_device_header_left_box{
    display: flex;
    justify-content: center;
    font-size: 20px;
    font-weight: 800;
}
/* 头部左边返回icon */
.lm_device_header_left_icon_box{
    display: flex;
    align-items: center;
    cursor:pointer;
    margin-right: 5px;
}
.lm_device_header_left_icon_box :hover{
    color: #a162f7;
}
/* 设备头部右边 */
.lm_device_header_right_box{
    margin-left: auto;
}
/* 设备数据展示区 */
.lm_device_data_box{

}
/* 设备信息区 */
.lm_device_data_info_box{
    margin-top: 10px;
    display: flex;
}
/* 设备信息标签盒子 */
.info_box{
    display: flex;
    align-items: center;
    font-size: 14px;
    height: 24px;
    margin-top: 1px;
}
.info_tag{
    color: #fff;
    padding: 2px 10px;
    background: #a162f7;
    border-radius: 5px;
    margin-right: 3px;
}
.info_val{
    margin-right: 10px;
}
/* 设备数据展示 */
.lm_device_data_ValData_box{
    margin-top: 10px;
}


/* 设备数据卡片盒子 */
.lm-card_box{
}
.lm-card_box:deep(.el-card__body){
    padding: 15px;
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
</style>