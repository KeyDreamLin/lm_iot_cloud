<template>
    <!-- 设备信息添加、修改信息框 -->
    <div class="lm_dialog_box">
        <el-dialog
            v-model="isShowDialog"
            :title="isUpadate?'修改物模型':'添加物模型'"
            width="30%"
            align-center
            @close="close"
            :close-on-press-escape="false">
            <div>
                <div class="p-2 pl-0">物模型名称</div>
                <el-input width="100%" v-model="deviceModelInfo.name" placeholder="请输入物模型名称"/>

                <div class="p-2 pl-0">物模型标识符</div>
                <!-- 不允许修改物模型标识符 -->
                <el-input width="100%" :disabled="isUpadate"  v-model="deviceModelInfo.identifier" placeholder="请输入物模型标识符"/>

                <div class="p-2 pl-0">数据类型</div>
                <el-select 
                    width="100%" 
                    v-model="deviceModelInfo.dataType" 
                    placeholder="请选择数据类型"
                    @change="selectDataTypeEvent"
                >
                    <el-option
                        v-for="item in deviceModelTypeList"
                        :key="item.dataType"
                        :label="item.dataType+'（'+item.dataName+'）'"
                        :value="item.dataType"
                    />
                </el-select>

                <template v-if="deviceModelInfo.dataType == 'bool'">
                    <div class="p-2 pl-0">布尔值</div>
                    <el-input class="" width="100%" v-model="deviceModelInfo.dataSpecs[1].name" placeholder="如：开">
                        <template #prepend>1-True&nbsp;</template>
                    </el-input>
                    <el-input class="mt-2" width="100%" v-model="deviceModelInfo.dataSpecs[0].name" placeholder="如：关">
                        <template #prepend>0-False</template>
                    </el-input>
                </template>
              
                <el-radio-group v-model="deviceModelInfo.modelType">
                    <el-radio :label=0 >传感器</el-radio>
                    <el-radio :label=1 >执行器</el-radio>
                </el-radio-group>

                <template v-if="deviceModelInfo.dataType != 'bool' && deviceModelInfo.dataType != 'text' ">
                    <div class="pb-2">单位</div>
                    <el-input width="100%" v-model="deviceModelInfo.unit" placeholder="请输入单位（选填） 如：℃"/>
                </template>
            </div>

            <div class="mt-2 ml-auto w-max">
                <el-button @click="update_add_event" :disabled="isPressBut" :loading="isPressBut"  type="info">确定</el-button>
                <el-button @click="close" type="cancel">取消</el-button>
            </div>
            {{deviceModelInfo}}

        </el-dialog>
    </div>
</template>

<script setup>
import deviceService from '@/services/device/DeviceService';
import deviceModelService from '@/services/devicemodel/DeviceModelService';
// 物模型数据类型服务
import deviceModelTypeService from '@/services/devicemodeltype/DeviceModelTypeService';

import { LmMessageError, LmMessageSuccess } from '@/utils';
import { update } from 'lodash';
import { ref } from 'vue';
// 用于获取当前路由的状态和地址
import { useRouter } from 'vue-router';

// 用于路由对象 对路由进行操作
const router = useRouter();
// 是否按下
const isPressBut = ref(false);
const isUpadate = ref(false);

const isShowDialog = ref(false);

// 设备物模型数据
const deviceModelInfo = ref({
    id: 0,               // 物模型id
    sn: "",              // 设备sn
    deviceId: 0,         // 设备id         
    name: "",            // 物模型名称
    identifier: "",      // 物模型标识符
    dataType: "",        // 物模型数据类型
    dataSpecs: "",       // 物模型类型说明
    modelType: 0,        // 物模型类型
    unit: "",            // 物模型单位
});
// 这个是为了给关闭对话框的时候置空的
const deviceModelInfoNull = {
    id: 0,               // 物模型id
    sn: "",              // 设备sn
    deviceId: 0,         // 设备id         
    name: "",            // 物模型名称
    identifier: "",      // 物模型标识符
    dataType: "",        // 物模型数据类型
    dataSpecs: "",       // 物模型类型说明
    modelType: 0,        // 物模型类型
    unit: "",            // 物模型单位
};

// 物模型数据类型列表
const deviceModelTypeList = ref([]);
// 获取物模型数据类型
const getDeviceModelType = (async()=>{
    
    let serverReponse =  await deviceModelTypeService.getDeviceModelTypeList();
    deviceModelTypeList.value = serverReponse.data;
    console.log("--->",deviceModelTypeList.value);
    deviceModelTypeList.value.forEach((item)=>{
        if(item.dataSpecs!=null&&item.dataSpecs!=undefined &&item.dataSpecs.length>0){
            if (typeof item.dataSpecs === "string") {
                item.dataSpecs = JSON.parse(item.dataSpecs);
            }
        }
    });

});
const open = (async (isupadate,deviceModel,deviceInfo)=>{
    console.log("设备物模型框-->外部传入的值",isupadate,deviceModel,deviceInfo);

    isUpadate.value = isupadate;

    // 将设备sn和设备id赋值    这里不管是更新还是新增都是赋值
    deviceModelInfo.value.sn = deviceInfo.sn;
    deviceModelInfo.value.deviceId = deviceInfo.id;
    await getDeviceModelType();
    if(isUpadate.value == true){
        console.log("打开修改物模型数据框-->",deviceModel);
        deviceModelInfo.value.id = deviceModel.id;
        deviceModelInfo.value.name = deviceModel.name;
        deviceModelInfo.value.identifier = deviceModel.identifier;
        deviceModelInfo.value.dataType = deviceModel.dataType;
        deviceModelInfo.value.dataSpecs = deviceModel.dataSpecs;
        deviceModelInfo.value.modelType = deviceModel.modelType;
        deviceModelInfo.value.unit = deviceModel.unit;
    }
    else if(isUpadate.value ==false){
        console.log("打开添加物模型数据框",deviceModel);
    }
    isShowDialog.value = true;
});
const close = (()=>{
    isShowDialog.value = false;
    let{...deviceModelInfoNull_1} = deviceModelInfoNull;
    deviceModelInfo.value = deviceModelInfoNull_1;
});
// select下拉框数据类型变动回调
const selectDataTypeEvent = ((val)=>{
    deviceModelTypeList.value.forEach(item=>{
        // 将数据类型的属性值名称返回出去 bool用到
        if(item.dataType == val){
            deviceModelInfo.value.dataSpecs = item.dataSpecs
        }
    });
    
});
// 抛出句柄 让父组件可以使用里面的方法 对外提供方法 
defineExpose({
    open,close
})
const update_add_event = (async ()=>{
    if(deviceModelInfo.value.name.length<=0 ){
        LmMessageError("请检查物模型名称是否为空!");
        return;
    }
    if(deviceModelInfo.value.identifier.length<=0){
        LmMessageError("请检查物模型标识符是否为空!");
        return;
    }
    if(deviceModelInfo.value.dataType.length<=0 ){
        LmMessageError("请选择物模型类型是否为空!");
        return;
    }

    isPressBut.value = true;
    // 判断空
    try {
        let temp = null;
        if(isUpadate.value == true){
            let serverReponse = await deviceModelService.updateDeviceModel(deviceModelInfo.value);
            console.log("更新状态>",serverReponse);
            LmMessageSuccess("更新成功");
        }
        else if (isUpadate.value == false){
            let serverReponse = await deviceModelService.addDeviceModel(deviceModelInfo.value);
            console.log("添加状态>",serverReponse);
            LmMessageSuccess("添加成功");
        }
        close();
        router.go(0);
    } catch (error) {
        LmMessageError("操作失败！");
    }
    isPressBut.value = false;
});
</script>

<style scoped>
.lm_dialog_box{

}
.lm_dialog_box :deep(.el-select){
    display: block;
}

.lm_dialog_box :deep(.el-dialog__body) {
    padding-top: 10px;
}
</style>