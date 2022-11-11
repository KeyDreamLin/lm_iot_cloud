<template>
    <!-- 设备信息列表 -->
    <div>
       <el-row :gutter="20" >
            <template v-for="(deviceinfo,index) in deviceData">
                <el-col :md="24" :lg="6" :xl="4" class="mb-5">
                    <div class="lm-device_box">
                        <el-card :body-style="{ padding: '10px' }" >
                            <template #header>
                                <div class="flex items-center justify-between">

                                    <!-- 设备名称 -->
                                    <el-tooltip content="进入设备管理" effect="light">
                                        <div @click="openDeivceInfo(deviceinfo)" class="lm-cursor-pointer">
                                            <span>{{deviceinfo.name}}</span>
                                        </div>
                                    </el-tooltip>

                                    <!-- 设备状态 -->
                                    <template v-if="deviceinfo.isOnLine==true">
                                        <el-tag type="success">在线</el-tag>
                                    </template>
                                    <template v-else>
                                        <div class="lm-tag_box">
                                            <el-tooltip content="点击复制鉴权JSON" effect="light">
                                                <el-tag @click="clickTag(deviceinfo)" type="danger">离线</el-tag>
                                            </el-tooltip>
                                        </div>
                                    </template>
                                </div>
                            </template>
                            <el-tooltip content="点击复制设备SN" effect="light">
                                <div @click="clickInfo(deviceinfo, true)" class="lm-device-info_box lm-cursor-pointer">
                                    <span class="lm-device-title">设备SN:</span>
                                    <span class="lm-device-info">{{deviceinfo.sn}}</span>
                                </div>
                             </el-tooltip>
                             <el-tooltip content="点击复制传输秘钥" effect="light">
                                <div @click="clickInfo(deviceinfo, false)" class="lm-device-info_box lm-cursor-pointer">
                                    <span class="lm-device-title">传输秘钥:</span>
                                    <span class="lm-device-info">{{deviceinfo.secretKey}}</span>
                                </div>
                             </el-tooltip>
                            <div class="lm-device-info_box">
                                <span class="lm-device-title">传输协议:</span>
                                <span class="lm-device-info">TCP</span>
                            </div>
                            <div class="lm-device-icon-but_box">
                                <el-tooltip content="查看设备信息" effect="light">
                                    <el-icon class="ml-auto lm-cursor-pointer"><Search /></el-icon>
                                </el-tooltip>
                                <el-tooltip content="删除设备" effect="light">
                                    <el-icon class="lm-cursor-pointer ml-3"><Delete /></el-icon>
                                </el-tooltip>
                            </div>
                        </el-card>
                    </div>
                </el-col> 
            </template>
       </el-row>
       <lm-device-info ref="DeivceDialogRef"></lm-device-info>
</div> 
  
</template>
<script setup>
import lmDeviceInfo from '@/components/device/LmDeviceInfo.vue'

import deviceService from '@/services/device/DeviceService';
import { onMounted, ref } from 'vue';
// 复制工具
import useClipboard from 'vue-clipboard3';
const { toClipboard } = useClipboard();
// 设备数据列表
const deviceData = ref([]);
// 分页查询设备信息
const pageDeive = async ()  =>{
    const deviceResponse = await deviceService.listPage({pageSize:20});
    deviceData.value = deviceResponse.data.records;
    console.log(deviceData.value);
}
// 设备离线的状态点击tag标签可以复制鉴权数据
const clickTag = async (deviceinfo) =>{
    let authJson = {
        "t":1,
        "sn":deviceinfo.sn,
        "secretKey":deviceinfo.secretKey
    }
    await toClipboard(JSON.stringify(authJson));
}
// 复制设备信息 sn或者设备传输秘钥
const clickInfo = async (deviceinfo ,isSn) =>{
    await toClipboard(isSn? deviceinfo.sn:deviceinfo.secretKey);
}
onMounted(()=>{
    pageDeive();
});


// 设备详情
const DeivceDialogRef = ref(null);
// 打开设备详情
const openDeivceInfo=(deviceData)=>{
    DeivceDialogRef.value.open(deviceData);
}
</script>
<style scoped>
.lm-device_box :deep(.el-card__header){
   padding: .9375rem;
   font-size: 1rem;

}
/* 设备信息盒子 */
.lm-device-info_box{
   padding: .3125rem;
   font-size: .625rem;
   display: flex;
   align-items: center;
}
/* 设备信息标题 */
.lm-device-title{
   width: 4.375rem;
   display: block;
   background: #b48bf7;
   padding: .1875rem .1875rem .1875rem .1875rem;
   margin-right: .1875rem;

   color: #fff;
   font-size: .75rem;
   font-weight: 500;
   text-align: center;
   border-radius: .3125rem;
}
/* 设备信息数据 */
.lm-device-info{
   display: block;
   width: 13.75rem;
   font-size: .75rem;
   white-space: nowrap;
   /* 溢出的文字会变成... */
   text-overflow: ellipsis;
   overflow: hidden;
}

.lm-cursor-pointer{
    /* 设置小手样式 */
    cursor:pointer;
}
/* 图标按键 */
.lm-device-icon-but_box{
    display: flex;
    color: #a162f7;
    font-size: 1.25rem;
    padding: .1875rem .1875rem .1875rem .1875rem;
}
</style>