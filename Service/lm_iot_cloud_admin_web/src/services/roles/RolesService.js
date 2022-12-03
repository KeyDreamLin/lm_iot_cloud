import lm_request from '@/utils/user_request.js'
export default {

    /**
     * 获取角色列表
     * @returns 
     */
     getRolesList() {
        let temp = lm_request.post("/role/list");
        return temp;
    }

}