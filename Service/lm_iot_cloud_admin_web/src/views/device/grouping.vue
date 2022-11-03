<template>
    <!-- 设备分组信息 -->
    <div class="lm_grouping_main_box">
        <!-- 分组头部 搜索等功能 -->
        <div class="lm_head">
            <div class="lm_head_left">
                <!-- 删除分组、添加分组 -->
                <el-button type="success">添加分组</el-button>
                <el-button type="danger">删除分组</el-button>
            </div>
            <!-- 搜索框 -->
            <div class="lm_head_right">
                <el-input v-model="keyword" placeholder="请输入分组名称关键词...">
                    <template #prefix>
                    </template>
                    <template #suffix>
                        <div class="lm_head_input_iconBut">
                            <el-icon><Search /></el-icon>
                        </div>
                    </template>
                </el-input>
            </div>
        </div>
        <!-- 分组数据盒子 -->
        <div class="lm_grouping_data_box">
            <el-table :data="deviceGrouping" style="width: 100%">
                <el-table-column prop="id" label="ID" width="200" />
                <el-table-column prop="name" label="分组名称"  />
                <el-table-column prop="createTime" label="创建时间" width="200" />
                <el-table-column prop="updateTime" label="更新时间" width="200" />
                <el-table-column  label="操作" >
                    <template #default="{ $index, row }">
                        <div>
                            <el-button type="success" @click="lookGroupingButEvent(row)">查看分组数据</el-button>
                            <el-button type="danger" @click="delGroupingButEvent(row)">删除分组数据</el-button>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
    <lm-device-grouping-info ref="LmDeviceGroupingInfoRef">

    </lm-device-grouping-info> 
</template>

<script setup>
import deviceGroupingService from '@/services/devicegrouping/DeviceGroupingService';
import LmDeviceGroupingInfo from '@/components/device/LmDeviceGroupingInfo.vue';
import { onMounted, ref } from 'vue';
const deviceGrouping = ref([]);
const LmDeviceGroupingInfoRef = ref(null);
const pageInit = async () => {
    let serReqGrouping = await deviceGroupingService.deviceGroupingPage();
    deviceGrouping.value = serReqGrouping.data.records;
    console.log("--------------------------------------------",serReqGrouping);
}
onMounted(()=>{
    pageInit();
});
// 查看分组数据
const lookGroupingButEvent = (Rrow) => {
    // console.log(Rrow);
    LmDeviceGroupingInfoRef.value.open(Rrow);
}
// 删除分组数据
const delGroupingButEvent = (Rrow) => {
    // console.log(Rrow);
}
</script>

<style scoped>
.lm_grouping_main_box{

}
.lm_head{
    padding: 1px;
    display: flex;
    align-items: center;
}
.lm_head_left{

}
.lm_head_right{
    display: flex;
    align-items: center;
    margin-left: auto;
}
.lm_head_right :deep(.is-focus){
    box-shadow: 0 0 0 1px rgba(161, 98, 247, 1);
}
.lm_head_right :deep(.el-input__wrapper){
    padding-right: 0;
}
.lm_head_input_iconBut{
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40px;
    height: 100%;
    cursor:pointer;
}
/* 分组数据盒子 */
.lm_grouping_data_box{
    margin-top: 15px;
}
</style>