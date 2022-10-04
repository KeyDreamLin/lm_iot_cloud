<template>
    <div class="lm-mian_box">
        <div class="lm-main-login_box">
            <div class="lm-main-login-title_box">
                <span class="text_title_1">
                    登录到后台管理.
                </span>
                <div class="text_title_2">
                    <span >这里是超级管理员登录地址!</span>
                    <span class="text_title_3 text_color">普通用户登录.</span>
                </div>
            </div>
      
            <!-- 分割线盒子 -->
            <div class="lm-main-login-line_box">
                <div class="line_right_box"></div>
                <div class="line_middle_box">or</div>
                <div class="line_left_box"></div>
            </div>
            <!-- 输入框盒子 -->
            <div class="lm-main-login-input_box">
                <el-form
                    label-position="top"
                    style="width: 100%"
                    size="large"
                >
                    <el-form-item label="账号">
                        <el-input v-model="adminLoginData.account" placeholder="请输入账号"/>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input 
                        v-model="adminLoginData.password" 
                        type="password" show-password placeholder="请输入密码"/>
                    </el-form-item>
                    <el-form-item label="验证码" class="lm-form_wrapper">
                        <el-input 
                            v-model="adminLoginData.code" 
                            placeholder="请输入验证码"
                            @keydown.enter = "AdminLoginEvent"
                        >
                            <template #suffix>
                                <img @click="newCodeEvent" class="code" :src="codeImg"/>
                            </template>
                        </el-input>
                    </el-form-item>

                </el-form>
            </div>
            <!-- 登录等功能按钮盒子 -->
            <div class="lm-main-but_box">
                <el-button @click="AdminLoginEvent" type="primary" >登录</el-button>
            </div>
        </div>
    </div>
</template>

<script setup>
import router from '@/router';
// 状态管理
import stroe from '@/stroe';
import adminLoginService from '@/services/admin_user/AdminLoginService';
import codeService from '@/services/code/CodeService.js';

import { onMounted, ref } from 'vue';

const codeImg = ref(null);
const adminLoginData = ref({
    account:"admin",
    password:"123456",
    code:"",
    codeuuid:"",
});

// 生成一个新的验证码
const newCodeEvent = async () =>{
    let serverReponseCode = await codeService.code();
    adminLoginData.value.codeuuid = serverReponseCode.data.codeuuid;
    codeImg.value = serverReponseCode.data.img;
    console.log("服务器回调:验证码uuid--------->",adminLoginData.value.codeuuid);
}
onMounted(()=>{
    newCodeEvent();
    // 后端5分钟清除Redis中的验证码，前端4分钟刷新，这样就不会后端Redis已经删除了这个验证码前端还显示这个验证码出来
    setInterval(newCodeEvent, 4 * 60 * 1000);
});
const AdminLoginEvent = async () => {
    // try{
    //     let serverReponse = await adminLoginService.login(adminLoginData.value);
    //     console.log("服务器回调:登录成功信息--------->",serverReponse.data);
    //     // 登录成功就跳转到后台
    //     router.push("/");
    // }catch(err){
    //     console.log("服务器回调:登录异常信息--------->",err);
    // }finally{
    //     // 不管你登录成功还是失败都刷新
    //     // 登录过一次就要刷新一次验证码 当然不刷后端也做处理了 同一个UUID返回异常
    //     newCodeEvent();
    // }
    try{
        let serverReponse = await stroe.dispatch("user/toLogin",adminLoginData.value);
        console.log("服务器回调:登录成功信息--------->",serverReponse);
        // 登录成功就跳转到后台
        router.push("/");
    }catch(err){
        console.log("服务器回调:登录异常信息--------->",err);
        // 登录失败就要刷新一次验证码 当然不刷后端也做处理了 同一个UUID返回异常
        newCodeEvent();
   }finally{
       
    }
}
</script>
<style scoped>

.lm-mian_box{
    width: 100%;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
}
/* 登录框 */
.lm-main-login_box{
    width: 360px;
}
/* 登录框标题文字 */
.lm-main-login-title_box{

}
/* 输入框 */
.lm-main-login-input_box :deep(.el-input__wrapper){
    border-radius: 8px;
}
.lm-main-login-input_box :deep(.is-focus){
    box-shadow: 0 0 0 1px rgba(161, 98, 247, 1);
}
.text_title_1{
    display: block;
    font-size: 30px;
    font-weight: 700;
    margin-bottom: 16px;
}
.text_title_2{
    font-size: 18px;
    font-weight: 500;
    letter-spacing: 0px;
    line-height: 27px;
    color: rgba(124, 124, 141, 1);
}
.text_title_3{
    font-size: 18px;
    font-weight: 500;
    letter-spacing: 0px;
    line-height: 27px;
    color: rgba(124, 124, 141, 1);
    margin-left:10px;
}
.text_color{
    color:rgba(161, 98, 247, 1);
    background: linear-gradient(to right,
            #505285 0%,
            #585e92 12%,
            #65689f 25%,
            #7474b0 37%,
            #7e7ebb 50%,
            #8389c7 62%,
            #9795d4 75%,
            #a2a1dc 87%,
            #b5aee4 100%);
    background: none;
}
/* 登录框标题文字 */

/* 分割线 */
.lm-main-login-line_box{
    height: 27px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px 0;

}
.line_right_box{
    width: 230px;
    height: 1px;
    opacity: 1;
    background: rgba(230, 232, 236, 1);
}
.line_middle_box{
    display: block;
    padding: 0 5px;
    color: rgba(119, 126, 144, 1);
}
.line_left_box{
    width: 230px;
    height: 1px;
    opacity: 1;
    background: rgba(230, 232, 236, 1);
}
/* 分割线 */
/* 用户输入框 */
.lm-main-login-input_box{
    width: 360px;
    border-radius: 10px;
    background: rgba(255, 255, 255, 1);
    border: 1px solid rgba(244, 245, 246, 1);            
    box-shadow: 0px 10px 110px 1px rgba(59, 59, 59, 0.08);
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    padding: 20px 20px 20px 20px;
}
.lm-main-login-input_box :deep(.el-form-item--large){
    margin-bottom:10px
}
.lm-form_wrapper :deep(.el-input__wrapper){
    padding-right: 0;
}
/* 验证码 */
.code{
   height: 38px;
   border-radius: 0 8px 8px 0;
   padding-right: .5px;
}
/* 用户输入框 */

/* 用户按钮 */
.lm-main-but_box{
    margin-top: 30px;
}
.lm-main-but_box :deep(.el-button){
    width: 100%;
    height: 50px;
    border-radius: 10px;
    background:#a162f7;
    border:none;
    /** 文本1 */
    font-size: 20px;
    font-weight: 700;
    letter-spacing: 0px;
    line-height: 0px;
    color: rgba(255, 250, 247, 1);
    text-align: left;
    vertical-align: top;
}
</style>