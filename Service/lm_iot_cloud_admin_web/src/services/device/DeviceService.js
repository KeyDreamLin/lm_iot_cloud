import lm_request from '@/utils/device_request.js'
export default {

    /**
     * 获取设备 信息 分页 查询指定设备
     * @returns 
     */
    list(params={}) {
        let temp = lm_request.post("/list",params);
        return temp;
    },
    /**
     * 根据sn查询出对应的设备信息
     * @param {*} sn 
     * @returns 
     */
    getInfoBySn(sn=""){
        let temp = lm_request.post(`/queryById/${sn}`);
        return temp;
    },
    /**
     * 获取最新的设备数据
     * @param {*} sn 
     * @returns 
     */
    getNewData(sn){
        console.log("snsnsnsnnsns",sn);
        let temp = lm_request.post(`/newdata/${sn}`);
        return temp;
    },
    /**
     * 获取设备是否在线
     * @param {} sn 
     */
    isOnLineBySn(sn){
        let temp = lm_request.post(`/online/${sn}`);
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
     * 添加一个设备
     * @param {*} params 
     * @returns 
     */
    addDevice(Iname){
        console.log("add");
        let params = {
            name:""
        }
        console.log("add---",Iname);
        params.name = Iname;
        let temp = lm_request.post("/add",params);
        return temp;
    },
    /**
     * 修改设备信息
     * @param {*} Iname 
     * @returns 
     */
    updateDevice(params={}){
        let temp = lm_request.post("/upd",params);
        return temp;
    },
    /**
     * 根据设备id删除一个设备 连带物模型数据一起删除
     * @param {设备id} did 
     * @returns 
     */
    delDevice(did){
        let temp = lm_request.post(`/del/${did}`);
        return temp;
    },
    /**
     * 发送命令到设备
     * @param {*} 
     * @returns 
     */
    cmd(params={}){
        let temp = lm_request.post(`/cmd`,params);
        return temp;
    },
    /**
     * 获取设备sn和设备name
     * @returns 
     */
    snname(){
        let temp = lm_request.post("/snname");
        return temp;
    },
    /**
     * 查询出平台的设备全部上报数 如果sn为null就查询全部的数据
     * @param {*} params 
     * @returns 
     */
    getDeviceDataUpCount(params={sn:null}){
        let temp = lm_request.post("/allupcount",params);
        return temp;
    },
    /**
     * 查询出今天平台的设备全部上报数 如果sn为null就查询全部的数据
     * @param {*} params 
     * @returns 
     */
    getThisDayDeviceDataUpCount(params={sn:null}){
        let temp = lm_request.post("/thisdayupcount",params);
        return temp;
    },
    /**
     * 查询平台有多少个数据
     * @param {*} params 
     * @returns 
     */
    getDeviceCount(){
        let temp = lm_request.post("/devicecount");
        return temp;
    },
    /**
     * 平台设备在线数
     * @param {*} params 
     * @returns 
     */
    getDeviceUpCount(){
        let temp = lm_request.post("/deviceupcount");
        return temp;
    },
    
    
    
}