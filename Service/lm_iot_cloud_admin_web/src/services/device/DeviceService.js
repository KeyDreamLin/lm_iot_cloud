import lm_request from '@/utils/device_request.js'
export default {

    /**
     * 获取设备分页查询
     * @returns 
     */
    listPage(params={}) {
        let temp = lm_request.post("/page",params);
        return temp;
    }

}