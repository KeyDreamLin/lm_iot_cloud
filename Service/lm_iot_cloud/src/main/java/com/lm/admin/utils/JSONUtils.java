package com.lm.admin.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;


public class JSONUtils {
    /**
     * json校验
     * @param str
     * @return
     */
    public static boolean isJSONValidate(String str){
        try {
            JSON.parse(str);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }
}