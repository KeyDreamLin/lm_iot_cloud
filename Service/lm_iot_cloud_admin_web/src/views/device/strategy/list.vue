<template>
    <!-- 策略 -->
    <div class="lm_strategy_main">
        <!-- 策略搜索添加 头部 -->
        <div class="lm_strategy_header">

            <div class="lm_strategy_header_left">
                <el-button @click="addStrteagyInfoEvent" type="success">添加规则</el-button>
            </div>
            <!-- <div class="lm_strategy_header_right">
                <el-input v-model="keyword" placeholder="请输入规则名称">
                    <template #prefix>
                    </template>
                    <template #suffix>
                        <div class="lm_strategy_header_input_iconBut">
                            <el-icon><Search /></el-icon>
                        </div>
                    </template>
                </el-input>
            </div> -->

        </div>
        <!-- 策略列表展示 -->
        <div class="lm_strategy_dataMian">
            <el-table 
                :data="table_strategy_data" style="width: 100%"
                :header-cell-style="{ textAlign: 'center' }"
                :cell-style="{ textAlign: 'center' }" 
            >
                <el-table-column prop="name" label="规则名称" width="180" />
                <el-table-column prop="describe" label="规则描述">
                    <template #default="scope">
                        <template v-if="scope.row.describe.length>0">
                            <span>{{scope.row.describe}}</span>
                        </template>
                        <template v-else>
                            <span>---</span>
                        </template>
                    </template>
                </el-table-column>
                <el-table-column prop="status" label="运行状态" width="100">
                    <template #default="scope">
                        <span>
                            {{ scope.row.status==0?"未启动":"运行中" }}
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" width="180" />
                <el-table-column  label="操作" width="300" >
                    <template #default="scope">
                        <el-tag class="mx-1 mt-1" color="#b48bf7" effect="dark" round @click="examineEvent(scope.row.id)">查看</el-tag>
                        <template v-if="scope.row.status==1">
                            <el-tag class="mx-1 mt-1" color="#b48bf7" @click="updStrategyStatusEvent(scope.row,0)" effect="dark" round>停止</el-tag>
                            <!-- <el-tag class="mx-1 mt-1" color="#b48bf7" effect="dark" round>触发</el-tag> -->
                        </template>
                        <template v-if="scope.row.status==0">
                            <el-tag class="mx-1 mt-1" color="#b48bf7" @click="updStrategyStatusEvent(scope.row,1)" effect="dark" round>启动</el-tag>
                        </template>
                        <!-- <el-tag class="mx-1 mt-1" color="#b48bf7" effect="dark" round>日记</el-tag> -->
                        <el-tag class="mx-1 mt-1" color="#b48bf7" effect="dark" @click="delStrategyEvent(scope.row)" round>删除</el-tag>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
    <lm-device-strteagy-info-dialog :is-upadate="false" ref="strteagyDialogRef"></lm-device-strteagy-info-dialog>
</template>


<script setup>
import LmDeviceStrteagyInfoDialog from '@/components/device/devicestrtegy/LmDeviceStrteagyInfoDialog.vue';
// 策略服务类
import deviceStrategyService from '@/services/devicestrategy/DeviceStrategyService';
import { LmMessageConfirm, LmMessageError, LmMessageSuccess } from '@/utils';
import { onMounted, ref } from 'vue';
// 用于路由对象 对路由进行操作
import { useRouter } from 'vue-router';
// 用于获取当前路由的状态和地址
import { useRoute } from 'vue-router';

// 用于路由对象 对路由进行操作
const router = useRouter();
// 用于获取当前路由的状态和地址
const route = useRoute();

// 策略表格数据
const table_strategy_data = ref([]);
// 获取策略分页列表
const getStrategyPage = ( async ()=>{
    let responseData = await deviceStrategyService.page({});
    // 服务器原始数据
    console.log("------>>>>",responseData);
    table_strategy_data.value = responseData.data;
    console.log("------",table_strategy_data.value);
});
// 查看策略详细信息
const examineEvent=((thisRowId)=>{
    router.push("/device/strategy/examine?id="+thisRowId);
});
// 更新策略状态
const updStrategyStatusEvent = (async (data,status)=>{
    console.log("更新策略状态------->",data,status);
    let msgret = await LmMessageConfirm("是否修改策略状态！","更新警告");
    if(msgret == true){
        try {
            data.status = status;
            let ret = await deviceStrategyService.update(data);
            LmMessageSuccess("更新策略状态成功!");
            await getStrategyPage();
        } catch (error) {
            LmMessageError(error.msg);
        }
    }
});
// 删除策略信息
const delStrategyEvent = (async (row)=>{
    let msgret = await LmMessageConfirm("是否删除策略信息","删除警告");
    if(msgret == true){
        try {
            // 删除设备策略服务
            let ret = await deviceStrategyService.delDeviceStrategy(row.id);
            console.log("删除策略信息-->",row);
            LmMessageSuccess("删除策略成功！");
            await getStrategyPage();
        } catch (error) {
            LmMessageError(error.msg);
        }
   
    }
});
// 添加策略信息 打开dialog
const addStrteagyInfoEvent = (()=>{
    strteagyDialogRef.value.open();
});
onMounted(()=>{
    getStrategyPage();
});


const strteagyDialogRef = ref(null);

</script>

<style scoped>
.lm_strategy_main_box{

}
.lm_strategy_header{
    padding: 1px;
    display: flex;
    align-items: center;
}
.lm_strategy_header_left{

}
.lm_strategy_header_right{
    display: flex;
    align-items: center;
    margin-left: auto;
}
.lm_strategy_header_right :deep(.is-focus){
    box-shadow: 0 0 0 1px rgba(161, 98, 247, 1);
}
.lm_strategy_header_right :deep(.el-input__wrapper){
    padding-right: 0;
}
.lm_strategy_header_input_iconBut{
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40px;
    height: 100%;
    cursor:pointer;
}
/* 策略展示主体 */
.lm_strategy_dataMian{
    margin-top: 15px;
}
/* tag标签设置鼠标小手 */
.lm_strategy_dataMian :deep(.el-tag ){
    cursor:pointer;
}
/* 去除tag标签的边框线 */
.lm_strategy_dataMian :deep(.el-tag--dark.el-tag){
    border:none;
}
</style>