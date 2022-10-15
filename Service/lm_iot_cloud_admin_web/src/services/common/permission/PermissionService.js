import lm_request from '@/utils/common_request.js'
export default {

    /**
     * 获取菜单信息-树
     * @returns 
     */
    menuTree() {
        let temp = lm_request.post("/menutree");
        return temp;
    }

}