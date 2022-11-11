<template>
    <!-- 策略 -->
    <div class="lm_strategy_main" v-if="$route.meta.isFather">
        <!-- 策略搜索添加 头部 -->
        <div class="lm_strategy_header">

            <div class="lm_strategy_header_left">
                <el-button type="success">添加规则</el-button>
            </div>
            <div class="lm_strategy_header_right">
                <el-input v-model="keyword" placeholder="请输入规则名称">
                    <template #prefix>
                    </template>
                    <template #suffix>
                        <div class="lm_strategy_header_input_iconBut">
                            <el-icon><Search /></el-icon>
                        </div>
                    </template>
                </el-input>
            </div>

        </div>
        <!-- 策略列表展示 -->
        <div class="lm_strategy_dataMian">
            <el-table :data="table_strategy_data" style="width: 100%"
            :header-cell-style="{ textAlign: 'center' }"
            :cell-style="{ textAlign: 'center' }" >
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
                        <el-tag class="mx-1 mt-1" type="info" color="#b48bf7" effect="dark" round>查看</el-tag>
                        <template v-if="scope.row.status==1">
                            <el-tag class="mx-1 mt-1" type="info" color="#b48bf7" effect="dark" round>停止</el-tag>
                            <el-tag class="mx-1 mt-1" type="info" color="#b48bf7" effect="dark" round>触发</el-tag>
                        </template>
                        <template v-if="scope.row.status==0">
                            <el-tag class="mx-1 mt-1" type="info" color="#b48bf7" effect="dark" round>启动</el-tag>
                        </template>
                        <el-tag class="mx-1 mt-1" type="info" color="#b48bf7" effect="dark" round>日记</el-tag>
                        <el-tag class="mx-1 mt-1" type="info" color="#b48bf7" effect="dark" round>删除</el-tag>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
    <div>
        <router-view v-slot="{ Component }">
            <transition name="fade">
                <keep-alive>
                    <component :is="Component"></component>
                </keep-alive>
            </transition>
        </router-view>
    </div>
</template>

<script setup>
// 策略服务类
import deviceStrategyService from '@/services/devicestrategy/DeviceStrategyService';
import { onMounted, ref } from 'vue';
// 策略表格数据
const table_strategy_data = ref([]);
// 获取策略分页列表
const getStrategyPage = ( async ()=>{
    let responseData = await deviceStrategyService.page({});
    // 服务器原始数据
    console.log("------>>>>",responseData);
    table_strategy_data.value = responseData.data.records;
    console.log("------",table_strategy_data.value);
});
onMounted(()=>{
    getStrategyPage();
});
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
.lm_strategy_dataMian :deep(.el-tag--dark.el-tag--info){
    border:none;
}
</style>