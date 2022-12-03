<template>
    <!-- 用户管理 -->
    <div class="child">
        <!-- 内容头部 -->
        <div class="header">
            <el-button type="success" @click="openAddUser">添加用户</el-button>
        </div>
        <!-- 内容主体 -->
        <div class="body">
            <el-table :data="userList" style="width: 100%"  
            :header-cell-style="{ textAlign: 'center' }"  
            :cell-style="{ textAlign: 'center' }" >
                <el-table-column prop="id" label="ID" width="180" />
                <el-table-column prop="username" label="名称" width="180" />
                <el-table-column prop="account" label="账号" />
                <el-table-column prop="roleName" label="角色" />
                <el-table-column prop="status" label="状态">
                    <template #default="scope">
                        <template v-if="scope.row.status==1">
                            <el-tag type="success">启用</el-tag>
                        </template>
                        <template v-if="scope.row.status==0">
                            <el-tag type="danger">禁用</el-tag>
                        </template>
                    </template>
                </el-table-column>
                <!-- <el-table-column prop="createTime" label="创建时间" /> -->
                <!-- <el-table-column prop="updateTime" label="更新时间" /> -->
                <el-table-column label="操作">
                    <template #default="scope">
                        <el-button type="success" @click="openUpdateUser(scope.row)">修改用户信息</el-button>
                        <!-- <el-button type="danger">删除用户信息</el-button> -->
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
    <lm-user-update-and-save-dialog ref="userDialogRef"></lm-user-update-and-save-dialog>
</template>
<script setup>
import lmUserUpdateAndSaveDialog from '@/components/user/LmUserUpdateAndSaveDialog.vue';

import userService from '@/services/user/UserService';
import { onActivated, ref } from 'vue';
const userDialogRef = ref(null);
const userList = ref([
    // {
    //     id:11,
    //     username:"11",
    //     account:"222",
    //     status:1,
    // }
]);
// 获取用户信息列表
const getUserList = (async ()=>{
    let serverRet = await userService.getUserList();
    userList.value = serverRet.data;
    console.log("获取到用户信息列表",serverRet);
});
const openUpdateUser = (async (rowData)=>{
    let {...copy} = rowData;
    console.log("打开修改信息",copy);
    userDialogRef.value.open(true,copy);
})
const openAddUser = (async (rowData)=>{
    userDialogRef.value.open(false,null);
})
onActivated(()=>{
    getUserList();  
});
</script>

<style scoped>
.child{

}
.header{
    margin-bottom: 15px;
}
.body{

}
</style>
