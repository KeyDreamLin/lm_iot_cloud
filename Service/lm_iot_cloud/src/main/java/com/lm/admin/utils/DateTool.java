package com.lm.admin.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateTool {

    // 设置日期格式
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 获取当前时间
     * @return
     */
    public static String getThisDateStr(){
        return simpleDateFormat.format(new Date()).toString();
    }

    /**
     * 前时间减开始时间，返回毫秒
     * @param thisDate 当前时间
     * @param signDate 签发时间
     * @return 返回毫秒
     */
    public static Long diffReturnMs(Date thisDate,Date signDate){
        Long diffMs = thisDate.getTime()-signDate.getTime();

        return diffMs;
    }

    /**
     * 当前时间减开始时间，返回分钟
     * @param thisDate 当前时间
     * @param signDate 签发时间
     * @return 返回分钟
     */
    public static Long diffReturnMinute(Date thisDate,Date signDate){
        Long diffMs = diffReturnMs(thisDate,signDate);
        Long diffMm = diffMs/(1000*60);
        return diffMm;
    }

}
