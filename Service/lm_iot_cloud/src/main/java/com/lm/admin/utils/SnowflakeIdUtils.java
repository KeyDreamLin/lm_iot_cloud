package com.lm.admin.utils;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;

import javax.annotation.PostConstruct;

/**
 * 生成雪花算法工具类
 * @author Lm
 * @date 2022/11/16 10:53
 */
public class SnowflakeIdUtils {
    // 创建 IdGeneratorOptions 对象，可在构造函数中输入 WorkerId：
    IdGeneratorOptions options = new IdGeneratorOptions();
    @PostConstruct
    public void init(){
       YitIdHelper.setIdGenerator(options);
    }
    public static Long getId(){
        return YitIdHelper.nextId();
    }
}
