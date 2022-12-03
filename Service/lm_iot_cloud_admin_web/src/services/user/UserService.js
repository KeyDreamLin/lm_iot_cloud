import lm_request from '@/utils/user_request.js'
export default {

    /**
     * 登录服务
     * @param {UserLoginVo} params 
     * @returns 
     */
    login(params={}) {
        let temp = lm_request.post("/login",params,{ isToken: false });
        return temp;
    },
    /**
     * 获取用户列表
     * @returns 
     */
    getUserList(){
        let temp = lm_request.post("/list");
        return temp;
    },
    /**
     * 添加一个用户
     * @param {*} params 
     * @returns 
     */
    addUser(params={}){
        let temp = lm_request.post("/add",params);
        return temp;
    },
    /**
     * 修改用户信息
     * @param {*} params 
     * @returns 
     */
    updUser(params={}){
        let temp = lm_request.post("/upd",params);
        return temp;
    },

}