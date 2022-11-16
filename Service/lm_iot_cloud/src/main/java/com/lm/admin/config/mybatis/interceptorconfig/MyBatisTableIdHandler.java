package com.lm.admin.config.mybatis.interceptorconfig;

import com.lm.admin.config.mybatis.annotation.FieldFill;
import com.lm.admin.config.mybatis.annotation.IdType;
import com.lm.admin.config.mybatis.annotation.TableField;
import com.lm.admin.config.mybatis.annotation.TableId;
import com.lm.admin.utils.SnowflakeIdUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * MyBatis 表id字段拦截器
 */
// 只拦截修改数据的 插入、修改sql语句
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MyBatisTableIdHandler implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        // 获取 SQL 命令
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();


        // 获取参数Mapper参数 对象
        Object parameter = invocation.getArgs()[1];

        // Mapper参数有两个 并且是Map 强转
        Map parameterMap = (Map) parameter;

        // 获取第一个参数 好像第一个参数和第二个参数是一样的 但是第二个参数的名字会变
        // 获取到Mapper的参数 对它进行修改就好了
        Object MapperParam = parameterMap.get("param1");

        // 通过getClass反射并 取出bean全部的字段
        Field[] fields = MapperParam.getClass().getDeclaredFields();

        for(Field field: fields){
            // 遍历 bean字段 上面是否 有我想要的注解
            if(field.isAnnotationPresent(TableId.class)){
                // 获取字段上面 注解的对象
                TableId TableIdAnnotation = field.getAnnotation(TableId.class);
                // 默认不处理
                if(TableIdAnnotation.type() == IdType.NONE) {
                    return invocation.proceed();
                }
                // 处理雪花id
                if(TableIdAnnotation.type() == IdType.ASSIGN_ID) {
                    // 判断SQL语句是否是 INSERT ，只有在插入的时候才能自动填充新的雪花id
                    if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                        // 设置为true java不会去检查权限控制
                        field.setAccessible(true);
                        // 设置bean字段的值
                        field.set(MapperParam, SnowflakeIdUtils.getId());
                    }
                }
                // 处理UUID
                if(TableIdAnnotation.type() == IdType.ASSIGN_ID) {
                    // 判断SQL语句是否是 INSERT ，只有在插入的时候才能自动填充新的UUID
                    if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                        // 设置为true java不会去检查权限控制
                        field.setAccessible(true);
                        // 设置bean字段的值
                        // TODO 用到了再说
                    }
                }
            }
        }
        return invocation.proceed();
    }

}