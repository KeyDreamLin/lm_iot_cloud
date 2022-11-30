<template>
    <!-- 设备分组信息 -->
    <div class="lm_grouping_main_box">
        <!-- 分组头部 搜索等功能 -->
        <div class="lm_grouping_header">
            <div class="lm_grouping_header_left">
                <!-- 删除分组、添加分组 -->
                <el-button type="success" @click="openAddDgEvent()">添加分组</el-button>
            </div>
            <!-- 搜索框 -->
            <!-- <div class="lm_grouping_header_right">
                <el-input v-model="keyword" placeholder="请输入分组名称关键词...">
                    <template #prefix>
                    </template>
                    <template #suffix>
                        <div class="lm_grouping_header_input_iconBut">
                            <el-icon><Search /></el-icon>
                        </div>
                    </template>
                </el-input>
            </div> -->
        </div>
        <!-- 分组数据盒子 -->
        <div class="lm_grouping_data_box">
            <el-table :data="deviceGrouping" style="width: 100%">
                <el-table-column prop="id" label="ID" width="200" />
                <el-table-column prop="name" label="分组名称" />
                <el-table-column prop="createTime" label="创建时间" width="200" />
                <el-table-column prop="updateTime" label="更新时间" width="200" />
                <el-table-column  label="操作"  width="280" >
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

    <lm-device-grouping-update-save-dialog :is-upadate="false" ref="dgDialogRef"></lm-device-grouping-update-save-dialog>
</template>

<script setup>
// 设备分组 添加修改 框
import lmDeviceGroupingUpdateSaveDialog from '@/components/device/LmDeviceGroupingUpdateSaveDialog.vue';
import deviceGroupingService from '@/services/devicegrouping/DeviceGroupingService';
import { LmMessageConfirm, LmMessageError, LmMessageSuccess } from '@/utils';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
// 用于获取当前路由的状态和地址
import { useRoute } from 'vue-router';
// 用于路由对象 对路由进行操作
const router = useRouter();
// 用于获取当前路由的状态和地址
const route = useRoute();
// 服务器返回的设备分组列表
const deviceGrouping = ref([]);
// 设备分组添加框ref
const dgDialogRef = ref(null);
const pageInit = async () => {
    let serReqGrouping = await deviceGroupingService.deviceGroupingPage();
    deviceGrouping.value = serReqGrouping.data;
    console.log("--------------------------------------------",serReqGrouping);
}
onMounted(()=>{
    pageInit();
});
// 查看分组数据
const lookGroupingButEvent = (Rrow) => {
    // console.log(Rrow);
    router.push("/device/grouping/lookOneInfo?id="+Rrow.id);

}
// 删除分组数据
const delGroupingButEvent = async (Rrow) => {
    let isdel = await LmMessageConfirm("是否删除设备分组!","删除警告");
    if(isdel == true){
        try {
            let ret = await deviceGroupingService.delDeviceGrouping(Rrow.id);
            console.log("删除设备分组-->",ret);
            await pageInit();  
            LmMessageSuccess("删除设备分组成功！");
        } catch (error) {
            LmMessageError(error.msg);
        }
     
    }
}
// 添加分组
const openAddDgEvent = (()=>{
    dgDialogRef.value.open();
});
</script>

<style scoped>
.lm_grouping_main_box{

}
.lm_grouping_header{
    padding: 1px;
    display: flex;
    align-items: center;
}
.lm_grouping_header_left{

}
.lm_grouping_header_right{
    display: flex;
    align-items: center;
    margin-left: auto;
}
.lm_grouping_header_right :deep(.is-focus){
    box-shadow: 0 0 0 1px rgba(161, 98, 247, 1);
}
.lm_grouping_header_right :deep(.el-input__wrapper){
    padding-right: 0;
}
.lm_grouping_header_input_iconBut{
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