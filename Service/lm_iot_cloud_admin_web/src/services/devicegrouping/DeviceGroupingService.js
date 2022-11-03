import lm_request from '@/utils/device_request.js'
export default {
    /**
     * 获取设备分组分页查询
     * @returns 
     */
    deviceGroupingPage(params={}) {
        let temp = lm_request.post("/devicegroupingpage",params);
        return temp;
    },

     /**
     * 获取设备分组分页查询
     * @returns 
     */
    deviceGroupigList(params) {
        let temp = lm_request.post("devicegrouping/devices/"+params);
        return temp;
    },
}