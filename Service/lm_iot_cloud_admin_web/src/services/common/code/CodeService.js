import lm_request from '@/utils/common_request.js'
export default {

    /**
     * 生成验证码接口
     * @returns 
     */
    code() {
        console.log("???????????");
        let temp = lm_request.post("/captcha");
        return temp;
    }

}