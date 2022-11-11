<template>
    <div class="lm-header_box">
        <div class="lm-header-container_box">
            <div class="lm-header-router-name_box">
                {{thisPathName}}
            </div>
            <div class="lm-header-user-info_box">
                <el-avatar 
                class="userImg_box"
                    shape="square" size="default" 
                    style="background: #e7e9ff; border-radius: 10px;"
                    :src="stroe.getters['user/getUserAvatar']" 
                />
                <div class="userNanme_box">
                    <span class="userName_vla">
                        {{stroe.getters["user/getUserName"]}}
                    </span>
                </div>
                <el-dropdown class="userIcon_box">
                    <span class="el-dropdown-link">
                        <el-icon class="el-icon--right">
                            <ArrowDownBold />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item>修改密码</el-dropdown-item>
                            <el-dropdown-item>退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
                <!-- <el-icon class="userIcon_box"><ArrowDownBold /></el-icon> -->
            </div>
        </div>
       
    </div>
</template>
<script setup>
// 状态管理
import { ref , onMounted, computed } from 'vue'
import stroe from '@/stroe';
// 用于获取当前路由的状态和地址
import { useRoute } from 'vue-router';
// 用于获取当前路由的状态和地址
const route = useRoute();
// 获取当前路由地址
const thisPathName = computed(() => { 
    console.log("---",route.meta);
    return route.meta.title; 
});

</script>
<style scoped>
    
.lm-header_box{
    width: 100%;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
}
/* 可视区域 */
.lm-header-container_box{
    width: 100%;
    height: 46px;
    display: flex;
    align-items: center;
    /* background: #aaa; */
}
/* 显示路由名字的盒子 */
.lm-header-router-name_box{
    /** 文本1 */
    font-size: 26px;
    font-weight: 700;
    letter-spacing: 0px;
    text-align: left;
    vertical-align: top;
}
/* 用户信息盒子 */
.lm-header-user-info_box{
    display: flex;
    align-items: center;
    width: 189px;
    height: 50px;
    margin-left: auto;
    border-radius: 10px;
    background: #fff;
    box-shadow: 0px 20px 40px 0px rgba(122, 137, 254, 0.05);
}
.userImg_box{
    margin: 5px 0 5px 5px;
}
.userNanme_box{
    margin: 0 15px;
    display: flex;
    justify-content: center;
    flex-grow: 1;
}
.userName_vla{
    width: 80px;
    text-align: center;
    white-space: nowrap;
    /* 溢出的文字会变成被截断 */
    text-overflow: clip;
    overflow: hidden;
}
.userIcon_box{
    color: #000;
    margin-right: 10px;
}
</style>