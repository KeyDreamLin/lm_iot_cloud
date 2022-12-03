<template>
    <!-- 设备分组添加、修改信息框 -->
    <div class="lm_dialog_box">
        <el-dialog
            v-model="isShowDialog"
            :title="isUpadate?'更新分组':'添加分组'"
            width="30%"
            align-center
            @close="close"
            :close-on-press-escape="false">
            <div>
                {{deviceGroupingInfo}}
                <div class="mb-1">分组名称</div>
                <el-input width="100%"  v-model="deviceGroupingInfo.name" placeholder="请输入分组名称"/>
                <template v-if="props.isUpadate == true">
                    <div class="mb-1 mt-1">选择设备添加到分组</div>
                    <el-select
                        v-model="deviceGroupingInfo.deviceIds"
                        multiple
                        filterable
                        allow-create
                        default-first-option
                        :reserve-keyword="false"
                        placeholder="请选择需要添加到分组的设备"
                    >
                    <el-option
                        v-for="item in deviceInfoList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    />
                    </el-select>
                </template>
            </div>
            <div class="mt-2 ml-auto w-max">
                <el-button @click="update_add_event" :disabled="isPressBut" :loading="isPressBut" type="info">确定</el-button>
                <el-button @click="close" type="cancel">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script setup>
import deviceService from '@/services/device/DeviceService';
import deviceGroupingService from '@/services/devicegrouping/DeviceGroupingService';

import { LmMessageError, LmMessageSuccess } from '@/utils';
import { update } from 'lodash';
import { ref } from 'vue';
// 用于获取当前路由的状态和地址
import { useRouter } from 'vue-router';

// 用于路由对象 对路由进行操作
const router = useRouter();
// 是否按下
const isPressBut = ref(false);
const isShowDialog = ref(false);

// 设备分组信息
const deviceGroupingInfo = ref({
    id:0,
    name:"",
    deviceIds:[],   // 设备id数组，用于将设备添加到设备分组中
});

// 服务器查询出来用户拥有的设备,用于将设备添加到设备分组中
const deviceInfoList = ref([]);

// 对外提供属性
const props = defineProps({
    // 是否是更新框,默认不是更新
    isUpadate:{
        type: Boolean,
        default: false
    },
});

const open = (async (DgInfo)=>{
    if(props.isUpadate == true){
        deviceGroupingInfo.value.id = DgInfo.id;
        deviceGroupingInfo.value.name = DgInfo.name;
        // 将用户拥有的设备查询出来
        let serverResponse = await deviceService.idsnname( deviceGroupingInfo.value.id);
        // 用户拥有的设备 放到 列表里面让用户选择
        deviceInfoList.value = serverResponse.data;
        console.log("查询到用户拥有的设备--->",deviceInfoList.value);
        // 查询分组拥有的设备
        let serverReqOwn = await deviceGroupingService.getGroupingOwnerDeviceById(deviceGroupingInfo.value.id);
        // 将查询出来的设备放到分组信息中
        deviceGroupingInfo.value.deviceIds = serverReqOwn.data;

        // 明天用select 联动 用户拥有的设备 和 分组拥有的设备
        // 然后 后端 根据 分组id 批量删除中间表的数据 然后将最新的数据批量插入进去
        // 先删除旧数据 添加新数据

        console.log("打开修改设备分组框",deviceGroupingInfo.value);
    }
    else if(props.isUpadate ==false){
        console.log("打开添加设备分组框");
    }
    isShowDialog.value = true;
});
const close = (()=>{
  
    isShowDialog.value = false;
});
// 抛出句柄 让父组件可以使用里面的方法 对外提供方法 
defineExpose({
    open,close
})
const update_add_event = (async ()=>{
    if(deviceGroupingInfo.value.name.length<=0){
        LmMessageError("请输入分组名称!");        
        return;
    }
    isPressBut.value = true;
    // 判断空
    try {
        let temp = null;
        if(props.isUpadate == true){
            let ret = await deviceGroupingService.updDeviceGrouping(deviceGroupingInfo.value);
            console.log("更新设备分组------>",ret);
            LmMessageSuccess("更新成功");
        }
        else if (props.isUpadate == false){
            let ret = await deviceGroupingService.addDeviceGrouping(deviceGroupingInfo.value);
            LmMessageSuccess("添加成功");
        }
        isPressBut.value = false;
        close();
        router.go(0);
    } catch (error) {
        LmMessageError(error.msg);
        console.log("添加或者删除设备分组--->",error);
    }
});
</script>

<style scoped>
.lm_dialog_box{

}
.lm_dialog_box :deep(.el-select){
    width:100%;
}
.lm_dialog_box :deep(.el-input) {
    width: 100%!important;
    display:  block !important;
} 
.lm_dialog_box :deep(.el-input__wrapper) {
    display: flex !important;
}
.lm_dialog_box :deep(.el-dialog__body) {
    padding-top: 10px;
}
</style>