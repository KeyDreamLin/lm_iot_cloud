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

}