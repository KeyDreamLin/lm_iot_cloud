package com.lm.admin.entity.bo.device;

import com.alibaba.fastjson2.annotation.JSONField;

import java.util.Map;

/**
 * @author Lm
 * @date 2022/10/9 16:02
 */
public class DeviceCmdBo {

    // 设备sn码
    private String sn;

    // 不可更改的
    @JSONField(ordinal = 1)
    private final Integer t = 5;

    @JSONField(ordinal = 2)
    private Map<String,Object> data; // 一般用于命令请求


    public Integer getT() {
        return t;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
