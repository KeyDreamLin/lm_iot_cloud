package com.lm.admin.tool.mybiats;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 分页查询bean
 * @author Lm
 * @date 2022/10/8 21:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Pager<T> {
    // 当前页 从一开始所以要减一 (pageIndex - 1) * pageSize
    private Integer pageIndex = 1;
    // 每页大小 一页有多少条数据
    private Integer pageSize = 10;
    // 总记录数  * count
    private Integer totalCount;
    // 总页数，可以通过每页大小和总记录数运算得来
    private Integer totalPageNum;
    // 当前页的数据
    private List<T> records;
}
