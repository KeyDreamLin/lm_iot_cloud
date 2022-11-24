import lm_request from '@/utils/device_request.js'
export default {
    /**
     * 获取设备分组分页查询
     * @returns 
     */
    deviceGroupingPage(params={}) {
        let temp = lm_request.post("/devicegrouping/list");
        return temp;
    },

     /**
     * 根据设备分组id查询到设备的信息列表
     * @returns params 分组id
     */
    deviceByGroupingId(params) {
        let temp = lm_request.post("/devices/groupingid/"+params);
        return temp;
    },
}