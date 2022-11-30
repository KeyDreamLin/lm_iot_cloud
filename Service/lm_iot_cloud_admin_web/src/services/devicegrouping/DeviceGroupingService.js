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
    /**
     * 根据设备分组id查询到设备的信息列表
     * @returns params 设备分组信息
     */
    addDeviceGrouping(params={}) {
        let temp = lm_request.post("/devicegrouping/add",params);
        return temp;
    },
    /**
     * 修改 分组信息  分组拥有设备
     * @param {*} params 
     * @returns 
     */
    updDeviceGrouping(params={}){
        let temp = lm_request.post("/devicegrouping/upd",params);
        return temp;
    },
    /**
     * 刪除 整个分组
     * @param {*} groupingId 分组信息 
     * @returns 
     */
    delDeviceGrouping(groupingId){
        let temp = lm_request.post(`/devicegroupiong/del/${groupingId}`);
        return temp;
    },
    /**
     * 查询设备分组拥有的设备 根据 分组id
     * @param {*} groupingId 分组id
     * @returns 
     */
    getGroupingOwnerDeviceById(groupingId){
        let temp = lm_request.post(`/devicegrouping/ownerdevice/${groupingId}`);
        return temp;
    },
    /**
     * 根据分组id查询分组信息
     * @param {*} groupingId 分组id
     * @returns 
     */
    getDeviceGroupingById(groupingId){
        let temp = lm_request.post(`/devicegrouping/querybyid/${groupingId}`);
        return temp;
    },
}