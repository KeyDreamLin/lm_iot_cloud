import lm_request from '@/utils/device_request.js'
export default {
    /**
     * 根据设备sn查询物模型的数据
     * @param {*} params 
     * @returns 
     */
    getDeviceModelBySn(params={}){
        let temp = lm_request.post("/devicemodel",params);
        return temp;
    },
    
    /**
     * 根据设备sn获取到物模型的数据名称和标识符
     * @param {*} params 
     * @returns 
     */
    getDeviceModelSelectData(params={}){
        let temp = lm_request.post("devicemodel/select",params);
        return temp;
    },

    getDeviceModelAllCount(){
        let temp = lm_request.post("/devicemodel/allcount");
        return temp;
    },
    
    getThisDayNewDeviceModelCount(){
        let temp = lm_request.post("/devicemodel/thisdaycount");
        return temp;
    },
    
    /**
     * 添加一条物模型数据
     * @param {*} params 
     * @returns 
     */
    addDeviceModel(params={}){
        let temp = lm_request.post("/devicemodel/add",params);
        return temp;
    },
    /**
     * 修改一条物模型数据
     * @param {*} params 
     * @returns 
     */
    updateDeviceModel(params={}){
        let temp = lm_request.post("/devicemodel/upd",params);
        return temp;
    },
    /**
     * 删除一条物模型数据
     * @param {物模型id} dmid 
     * @returns 
     */
    delDeviceModel(dmid){
        let temp = lm_request.post(`devicemodel/del/${dmid}`);
        return temp;
    }

}