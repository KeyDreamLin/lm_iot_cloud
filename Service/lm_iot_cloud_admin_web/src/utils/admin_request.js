// 请求工具类
import axios from 'axios'
// 状态管理
import store from '@/store';
// 路由管理 
import router from '@/router';
import errorCode from '@/utils/errorCode';
// 导入弹窗工具类
import { LmMessageError } from "@/utils/index.js";

// create an axios instance
const lm_request_admin = axios.create({
    baseURL: "/api/admin",
    timeout: 5000,
    isToken: true, // 是否需要带token请求
})

// 添加请求拦截器
lm_request_admin.interceptors.request.use((config) => {
    return config
}, error => {
    console.log("server request error-->", error) // for debug
})


// 添加响应拦截器
lm_request_admin.interceptors.response.use((response) => {
    // let res_data = response.data;
    return Promise.reject(response);
    // return response;
}, function (err) {
    return err;
});

export default lm_request_admin;