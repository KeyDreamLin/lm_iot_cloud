package com.lm.strategytask.V1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 策略执行命令 bean
 * @author Lm
 * @date 2022/11/8 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StrategyExecuteCmd implements Serializable {

    // 设备sn码
    private String sn;

    // 设备标识符
    private String identifier;

    // 发送的指令
    private String data;

    /**
     * 转换为时间戳 ms   10 * 1000
     * 这个是时间戳 当前时间戳加上需要延时的时间ms
     * 延时任务判断一下当前时间大于等于executeJobTimestamp执行任务 并删除
     */
    private long executeJobTimestamp;
    // 原始的时间戳
    private long executeJobTime;

}
