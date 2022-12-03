<template>
    <div class="lm-mian_box">
        <div class="lm-main-login_box">
            <div class="lm-main-login-title_box">
                <span class="text_title_1">
                    {{is_signUp ? "注册" : "登录到" }} LmCloud.
                </span>
                <div class="text_title_2">
                    <span >
                        {{is_signUp?  "已经有账号了吗？" : "没有注册账号吗?"}}
                    </span>
                    <span class="text_title_3 text_color" @click="switch_signUp_event">
                        {{is_signUp?"点击登录" : "点击注册账号!"  }}
                    </span>
                </div>
            </div>
      
            <!-- 分割线盒子 -->
            <div class="lm-main-login-line_box">
                <div class="line_right_box"></div>
                <div class="line_middle_box">
                    {{is_signUp ? "注册" : "登录" }}
                </div>
                <div class="line_left_box"></div>
            </div>
            <!-- 输入框盒子 -->
            <div class="lm-main-login-input_box">
                <el-form
                    label-position="top"
                    style="width: 100%"
                    size="large"
                >
                    <transition name="Fade" >
                        <el-form-item label="用户名"  v-if="is_signUp">
                            <el-input  placeholder="请输入用户名"/>
                        </el-form-item>
                    </transition>
                    <el-form-item label="账号">
                        <el-input v-model="viewData.account" placeholder="请输入账号"/>
                    </el-form-item>

                    <el-form-item label="密码">
                        <el-input 
                            v-model="viewData.password"
                            type="password" 
                            show-password placeholder="请输入密码"
                        />
                    </el-form-item>
                    <transition name="Fade" >
                        <el-form-item label="重复密码" v-if="is_signUp">
                            <el-input 
                                type="password" 
                                show-password placeholder="再次输入密码"
                            />
                        </el-form-item>
                    </transition>
                    <el-form-item label="验证码" class="lm-form_wrapper"  >
                        <el-input 
                            v-model="viewData.code" 
                            placeholder="请输入验证码"
                            @keydown.enter = "login_or_sign_in_event"
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
                <el-button type="primary" @click="login_or_sign_in_event" >
                    {{is_signUp ? "注册" : "登录" }}
                </el-button>
            </div>
        </div>
    </div>
</template>

<script setup>
import router from '@/router';
import { onMounted, ref } from 'vue';
import codeService from '@/services/common/code/CodeService.js';
import userService from '@/services/user/UserService';
import storage from '@/storage';
import { LmMessageError, LmMessageSuccess } from '@/utils';
import * as utils from '@/utils/index.js';

// 是否是注册状态
const is_signUp = ref(false);
// 验证码图片
const codeImg = ref(null);
// 切换登录或者注册
const switch_signUp_event = () => {
    is_signUp.value = !is_signUp.value;
}
// 注册或者登录事件
const login_or_sign_in_event = async () => {
    // alert(is_signUp.value ? "注册状态" : "登录状态" );
    if(is_signUp.value == false){
        login();
    }
    else if(is_signUp.value == true){
        signup();
    }
}
const login = (async ()=>{
    let {username,doublePassword,...loginData} = viewData.value;

    try{
        loginData.password = utils.encryptByDES(loginData.password);
        
        let serverReponse = await storage.dispatch("user/toLogin",loginData);
        console.log("服务器回调:登录成功信息--------->",serverReponse);
        // 登录成功就跳转到后台
        router.push("/");
        LmMessageSuccess("登录成功！");
    }catch(err){
        console.log("服务器回调:登录异常信息--------->",err);
        // 登录失败就要刷新一次验证码 当然不刷后端也做处理了 同一个UUID返回异常
        newCodeEvent();
   }finally{
       
    }
});
const signup = (()=>{

});
// view数据 界面的数据
const viewData = ref({
    account:"xiaoma",  // 账号
    username:"",   // 用户名   - 注册
    password:"wenhao",  // 密码
    doublePassword:"" ,  // 重复输入密码 - 注册
    code:"",  // 验证码
    codeuuid:"", 
});

// 生成一个新的验证码
const newCodeEvent = async () =>{
    console.log("服务器回调:验证码uuid--------->");
    let serverReponseCode = await codeService.code();
    viewData.value.codeuuid = serverReponseCode.data.codeuuid;
    codeImg.value = serverReponseCode.data.img;
    console.log("服务器回调:验证码uuid--------->",viewData.value);
}
onMounted( async()=>{
    // 生成验证码
    await newCodeEvent();
});

</script>
<style scoped>

    
.Fade-enter,
.Fade-leave-to {
	opacity: 0;
}
.Fade-enter-to,
.Fade-leave {
	opacity: 1;
}

.Fade-enter-active,
.Fade-leave-active {
	transition: all .4s;
}	

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
    display: flex;
    font-size: 30px;
    font-weight: 700;
    margin-bottom: 16px;
    align-items: center;
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
    /* 设置小手样式 */
    cursor:pointer;
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
    flex-grow: 1;
    height: 1px;
    opacity: 1;
    background: rgba(230, 232, 236, 1);
}
.line_middle_box{
    padding: 0 5px;
    color: rgba(119, 126, 144, 1);
}
.line_left_box{
    flex-grow: 1;
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
/* 用户输入框 */
/* 用户按钮 */
.lm-main-but_box{
    margin-top: 30px;
}
.lm-main-but_box :deep(.el-button){
    width: 100%;
    height: 50px;
    border-radius: 10px;
    background: rgba(161, 98, 247, 1);
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


.lm-form_wrapper :deep(.el-input__wrapper){
    padding-right: 0;
}
/* 验证码 */
.code{
   height: 38px;
   border-radius: 0 8px 8px 0;
   padding-right: .5px;
}
</style>