package com.lm.admin.tool.mybiats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    /**
     * 当前页
     */
    private int pageIndex;
    /**
     * 每页大小
     */
    private int pageSize;
    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 总页数，可以通过每页大小和总记录数运算得来
     */
    private int totalPageNum;
    /**
     * 当前页的数据
     */
    private List<T> list;
}
