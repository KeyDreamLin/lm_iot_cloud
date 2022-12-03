<template>
    <!-- 设备信息添加、修改信息框 -->
    <div class="lm_dialog_box">
        <el-dialog
            v-model="isShowDialog"
            :title="isUpadate?'更新用户信息':'添加用户信息'"
            width="30%"
            align-center
            @close="close"
            :close-on-press-escape="false">
            <div>
                <div class="p-2 pl-0">姓名</div>
                <el-input width="100%"  v-model="userInfo.username" placeholder="请输入用户名称"/>
                <div class="p-2 pl-0">账号</div>
                <el-input width="100%" :disabled="isUpadate"  v-model="userInfo.account" placeholder="请输入用户账号"/>
                <div class="p-2 pl-0">密码</div>
                <el-input width="100%"  v-model="userInfo.password" placeholder="请输入用户密码"/>
                <div class="p-2 pl-0">
                    <sapn class="mr-2">状态</sapn>
                    <el-switch v-model="userInfo.status" :active-value="1" :inactive-value="0"/>
                </div>

                <el-select v-model="userInfo.roleId"  placeholder="请选择用户角色">
                    <el-option
                        v-for="item in rolesList"
                        :key="item.id"
                        :label="item.roleName"
                        :value="item.id"
                    />
                </el-select>
                {{userInfo}}
            </div>
            <div class="mt-2 ml-auto w-max">
                <el-button @click="update_add_event" :disabled="isPressBut" :loading="isPressBut"  type="info">确定</el-button>
                <el-button @click="close" type="cancel">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script setup>
import deviceService from '@/services/device/DeviceService';
import rolesService from '@/services/roles/RolesService';
import UserService from '@/services/user/UserService';
import * as utils from '@/utils/index.js';

import { LmMessageError, LmMessageSuccess } from '@/utils';
import { update } from 'lodash';
import { ref } from 'vue';
// 用于获取当前路由的状态和地址
import { useRouter } from 'vue-router';
// 是否按下
const isPressBut = ref(false);
// 用于路由对象 对路由进行操作
const router = useRouter();

const isShowDialog = ref(false);

const userInfo = ref({
    id:0,
    username:"",
    account:"",
    avatar:"https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
    password:"",
    roleId:2,
    roleName: "",
    status:0
});
const userInfoCopy ={
    id:0,
    username:"",
    account:"",
    avatar:"https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
    password:"",
    roleId:2,
    roleName: "",
    status:0
};
// 数据库的角色信息
const rolesList = ref([
    {
        id:1,
        roleCode:"",
        roleName:"",
        status:"",
        createTime:"",
        updateTime:""
    }
]);
const isUpadate = ref(false);

const open = (async(retIsUpdate,userinfo)=>{
    isUpadate.value = retIsUpdate;
    // 获取数据库的角色列表 
    let serverRet = await rolesService.getRolesList();
    rolesList.value = serverRet.data;
    console.log("-角色信息--->",rolesList.value);
    if(isUpadate.value == true){
        userInfo.value.id = userinfo.id;
        userInfo.value.username = userinfo.username;
        userInfo.value.account = userinfo.account;
        userInfo.value.avatar = userinfo.avatar;
        userInfo.value.password = "";
        userInfo.value.roleId = userinfo.roleId;
        userInfo.value.roleName = userinfo.roleName;
        userInfo.value.status = userinfo.status;
   
        console.log("打开修改用户信息-->",userInfo.value);
    }
    else if(isUpadate.value ==false){
        console.log("打开添加用户信息");
    }
    isShowDialog.value = true;
});
const close = (()=>{
    // 深度拷贝
    let {...userInfoCopy1} = userInfoCopy;
    userInfo.value = userInfoCopy1;
    isShowDialog.value = false;
});
// 抛出句柄 让父组件可以使用里面的方法 对外提供方法 
defineExpose({
    open,close
})
const update_add_event = (async ()=>{
    if(userInfo.value.username.length<=0){
        LmMessageError("请输入用户名称");
        return;
    }
    if(userInfo.value.account.length<=0){
        LmMessageError("请输入用户账号");
        return;
    }
    if(userInfo.value.password.length<=0){
        LmMessageError("请输入用户密码");
        return;
    }
   
    // 判断空
    isPressBut.value = true;
    try {
        let temp = null;
        let {...userInfoCopyPass} = userInfo.value;
        userInfoCopyPass.password = utils.encryptByDES(userInfoCopyPass.password);

        if(isUpadate.value == true){
            let ret = await UserService.updUser(userInfoCopyPass);
            LmMessageSuccess("更新成功");
        }
        else if (isUpadate.value == false){
            let ret = await UserService.addUser(userInfoCopyPass);
            LmMessageSuccess("添加成功");
            console.log("添加用户",ret);
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
    width:100%;
}
.lm_dialog_box :deep(.el-input) {
    width: 100%!important;
    display:  flex !important;
} 
.lm_dialog_box :deep(.el-input__wrapper) {
    display:  flex !important;
}
.lm_dialog_box :deep(.el-dialog__body) {
    padding-top: 10px;
}
</style>