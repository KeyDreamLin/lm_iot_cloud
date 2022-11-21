import lm_request from '@/utils/device_request.js'
export default {
    /**
     * 获取设备分组分页查询
     * @returns 
     */
     page(params={}) {
        let temp = lm_request.post("/devicestrategy/page",params);
        return temp;
    },
    getInfoById(sid=-1){
        if(sid==-1){return}
        let temp  = lm_request.post("/devicestrategy/"+sid);
        return temp;
    },
    /**
     * 更新策略信息
     * @param {策略对象} params 
     * @returns 
     */
    update(params={}){
        let temp = lm_request.post("/devicestrategy/upd",params);
        return temp;
    },
    /**
     * 更新策略信息
     * @param {策略对象} params 
     * @returns 
     */
    add(params={}){
        let temp = lm_request.post("/devicestrategy/add",params);
        return temp;
    },
    /**
     * 获取平台设备策略总数
     * @returns 
     */
    getAllCount(){
        let temp = lm_request.post("/devicestrategy/allcount");
        return temp;
    },
    /**
     * 获取平台今天新增策略数据总数
     * @returns 
     */
    getOpenCount(){
        let temp = lm_request.post("/devicestrategy/opencount");
        return temp;
    },

}