// 请求工具类
import axios from 'axios'
// // 状态管理
// import store from '@/store';
// 路由管理 
import router from '@/router';
// import errorCode from '@/utils/errorCode';
// // 导入弹窗工具类
// import { LmMessageError } from "@/utils/index.js";

// create an axios instance
const lm_request_user = axios.create({
    baseURL: "/api/user",
    timeout: 5000,
    isToken: true, // 是否需要带token请求
})

// 添加请求拦截器
lm_request_user.interceptors.request.use((config) => {
    if(config.isToken == true){
        config.headers['token_Jj'] = storage.getters["user/getTokenJj"];
        config.headers['user_id'] = storage.getters["user/getUserId"];
        config.headers['user_code'] = storage.getters["user/getRoleCode"];
    }
    return config
}, error => {
    console.log("server request error-->", error) // for debug
})


// 添加响应拦截器
lm_request_user.interceptors.response.use((response) => {
    let res_data = response.data;

    // 判断一下是否是object类型 
    // 因为后端如果直接发string的话JSON就会被序列化为string而不是object
    if (typeof res_data === "string") {
        res_data = JSON.parse(res_data);
    }

    if(res_data.code == 200){
        return (res_data);
    }
    return Promise.reject(res_data);

}, function (err) {
    return err;
});

export default lm_request_user;