import lm_request from '@/utils/device_request'
export default {
    
    /**
     * 获取物模型数据类型列表
     */
    getDeviceModelTypeList(){
        let temp = lm_request.post("/devicemodeltype/list");
        return temp;
    },

}