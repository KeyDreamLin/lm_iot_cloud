<template>
    <!-- 设备策略添加、修改信息框 -->
    <div class="lm_dialog_box">
        <el-dialog
            v-model="isShowDialog"
            :title="isUpadate?'更新场景联动规则':'添加场景联动规则'"
            width="30%"
            align-center
            @close="close"
            :close-on-press-escape="false">
            <div>
                <div class="p-2 pl-0">规则名称</div>
                <el-input width="100%"  v-model="strteagy.name" placeholder="请输入规则名称"/>
            </div>
            <div>
                <div class="p-2 pl-0">规则描述</div>
                <el-input
                    v-model="strteagy.describe"
                    :autosize="{ minRows: 2, maxRows: 4 }"
                    maxlength="100"
                    placeholder="请输入规则描述"
                    show-word-limit
                    type="textarea"
                />
            </div>
            <div class="mt-2 ml-auto w-max">
                <el-button @click="update_add_event"  :disabled="isPressBut" :loading="isPressBut"  type="info">确定</el-button>
                <el-button @click="close" type="cancel">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script setup>
import deviceStrategyService from '@/services/devicestrategy/DeviceStrategyService';
import { LmMessageError, LmMessageSuccess } from '@/utils';
import { ref } from 'vue';
// 用于获取当前路由的状态和地址
import { useRouter } from 'vue-router';

// 用于路由对象 对路由进行操作
const router = useRouter();
// 是否按下
const isPressBut = ref(false);
const isShowDialog = ref(false);
const strteagy = ref({
    id:0,
    name:"",
    describe:"",
});
// 对外提供属性
const props = defineProps({
    // 是否是更新框,默认不是更新
    isUpadate:{
        type: Boolean,
        default: false
    },

});
const open = ((strteagy_)=>{
    if(props.isUpadate == true){
        strteagy.value.id = strteagy_.id;
        strteagy.value.name = strteagy_.name;
        strteagy.value.describe = strteagy_.describe;
    }
    else if(props.isUpadate ==false){
        strteagy.value.id = 0;
        strteagy.value.name = "";
        strteagy.value.describe = "";
    }
   
    isShowDialog.value = true;
});
const close = (()=>{
    strteagy.value.name = "";
    strteagy.value.describe = "";
    isShowDialog.value = false;
});
// 抛出句柄 让父组件可以使用里面的方法 对外提供方法 
defineExpose({
    open,close
})
const update_add_event = (async ()=>{
    if(strteagy.value.name.length <= 0){
        LmMessageError("请输入规则名称！");
        return;
    }
    isPressBut.value = true;
    try {
        let temp = null;
        if(props.isUpadate == true){
            temp = await deviceStrategyService.update(strteagy.value);
            if(temp.data!=null && temp.data>0){
                LmMessageSuccess("更新规则成功！");
            }
            else{
                LmMessageError("更新规则失败！");
            }
        }
        else if (props.isUpadate == false){
            temp = await deviceStrategyService.add(strteagy.value);
            if(temp.data!=null && temp.data>0){
                LmMessageSuccess("添加规则成功！");
            }
            else{
                LmMessageError("添加规则失败！");
            }
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