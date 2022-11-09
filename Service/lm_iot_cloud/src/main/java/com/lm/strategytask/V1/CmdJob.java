package com.lm.strategytask.V1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 命令任务对象 - 用于自动控制策略
 * @author Lm
 * @date 2022/11/8 9:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmdJob implements Serializable {
    // 1 {dslkfhlahvhvdslhghwo2_led}:1:10 | {test_led1}:1:0
    // 如果重复放入任务的话，延时的时间 = 延时的时间 + 1
    /**
     * 延迟任务的唯一标识，用于检索任务 使用mysql策略表的id作为任务的唯一表示 1
     */
    private Long id;

    // 执行任务策略的动作 发送命令列表
    private List<StrategyExecuteCmd> strategyExecuteCmdList;


}
