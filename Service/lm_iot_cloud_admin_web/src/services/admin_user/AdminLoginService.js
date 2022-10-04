import lm_admin_request from '@/utils/admin_request.js'
export default {

    /**
     * 登录服务
     * @param {AdminUserLoginVo} params 
     * @returns 
     */
    login(params={}) {
        let temp = lm_admin_request.post("/login",params);
        return temp;
    }

}