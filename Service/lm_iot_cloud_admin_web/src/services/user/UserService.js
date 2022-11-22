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
    }

}