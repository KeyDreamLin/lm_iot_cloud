<template>
    <!-- 设备信息添加、修改信息框 -->
    <div class="lm_dialog_box">
        <el-dialog
            v-model="isShowDialog"
            :title="isUpadate?'更新设备':'添加设备'"
            width="30%"
            align-center
            @close="close"
            :close-on-press-escape="false">
            <div>
                <div class="p-2 pl-0">设备名称</div>
                <el-input width="100%"  v-model="deviceInfo.name" placeholder="请输入设备名称"/>
                <template v-if="isUpadate==true">
                    <div class="p-2 pl-0">设备唯一标识符</div>
                    <el-input width="100%" :disabled="true" v-model="deviceInfo.sn"/>
                    <div class="p-2 pl-0">设备传输秘钥</div>
                    <el-input width="100%" :disabled="true" v-model="deviceInfo.secretKey"/>
                </template>
            </div>
            <div class="mt-2 ml-auto w-max">
                <el-button @click="update_add_event" type="info">确定</el-button>
                <el-button @click="close" type="cancel">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script setup>
import deviceService from '@/services/device/DeviceService';

import { LmMessageError, LmMessageSuccess } from '@/utils';
import { update } from 'lodash';
import { ref } from 'vue';
// 用于获取当前路由的状态和地址
import { useRouter } from 'vue-router';

// 用于路由对象 对路由进行操作
const router = useRouter();

const isShowDialog = ref(false);

const deviceInfo = ref({
    id:0,               // 这个用于设备更新信息而已
    name:"",            // 设备名称 用于添加修改
    sn:"",              // sn是不可更改的 只是在更新设备信息的时候展示一下
    secretKey:"",      // 传输秘钥也是不可更改的 只是在更新设备信息的时候展示一下
});

// 对外提供属性
const props = defineProps({
    // 是否是更新框,默认不是更新
    isUpadate:{
        type: Boolean,
        default: false
    },

});
const open = ((device)=>{
    if(props.isUpadate == true){
        console.log("打开修改设备框-->",device);
        deviceInfo.value.id = device.id;
        deviceInfo.value.name = device.name;
        deviceInfo.value.sn = device.sn;
        deviceInfo.value.secretKey = device.secretKey;
    }
    else if(props.isUpadate ==false){
        deviceInfo.value.id = 0;
        deviceInfo.value.name = "";
        deviceInfo.value.sn = "";
        deviceInfo.value.secretKey = "";
        console.log("打开添加设备框");
    }
    isShowDialog.value = true;
});
const close = (()=>{
    deviceInfo.value.id = 0;
    deviceInfo.value.name = "";
    deviceInfo.value.sn = "";
    deviceInfo.value.secretKey = "";
    isShowDialog.value = false;
});
// 抛出句柄 让父组件可以使用里面的方法 对外提供方法 
defineExpose({
    open,close
})
const update_add_event = (async ()=>{
    // 判断空
    try {
        let temp = null;
        if(props.isUpadate == true){
            await deviceService.updateDevice(deviceInfo.value);
            LmMessageSuccess("更新成功");
        }
        else if (props.isUpadate == false){
            await deviceService.addDevice(deviceInfo.value.name);
            LmMessageSuccess("添加成功");
        }
        close();
        router.go(0);
    } catch (error) {
        LmMessageError("操作失败！");
    }
});
</script>

<style scoped>
.lm_dialog_box{

}
.lm_dialog_box :deep(.el-input) {
    width: 100%!important;
    display:  block !important;
} 
.lm_dialog_box :deep(.el-input__wrapper) {
    display:  block !important;
}
.lm_dialog_box :deep(.el-dialog__body) {
    padding-top: 10px;
}
</style>