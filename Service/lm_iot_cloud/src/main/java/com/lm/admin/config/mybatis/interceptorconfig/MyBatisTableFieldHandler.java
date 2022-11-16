package com.lm.admin.config.mybatis.interceptorconfig;

import com.lm.admin.config.mybatis.annotation.FieldFill;
import com.lm.admin.config.mybatis.annotation.TableField;
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
 * MyBatis表字段拦截器
 */
// 只拦截修改数据的 插入、修改sql语句
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MyBatisTableFieldHandler implements Interceptor {
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
            if(field.isAnnotationPresent(TableField.class)){
                // 获取字段上面 注解的对象
                TableField TableFieldAnnotation = field.getAnnotation(TableField.class);
                // 不执行 修改bean字段
                if(TableFieldAnnotation.fill() == FieldFill.DEFAULT){
                    return invocation.proceed();
                }
                // 如果注解是 FieldFill.UPDATE的话
                if(TableFieldAnnotation.fill() == FieldFill.UPDATE){
                    // 先判断 SQL是不是UPDATE语句
                    if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                        // 设置为true java不会去检查权限控制
                        field.setAccessible(true);
                        // 设置bean字段的值
                        field.set(MapperParam, new Date(System.currentTimeMillis()));
                    }
                }
                // 如果注解是 FieldFill.INSERT的话
                if(TableFieldAnnotation.fill() == FieldFill.INSERT){
                    // 先判断 SQL是不是INSERT语句
                    if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                        // 设置为true java不会去检查权限控制
                        field.setAccessible(true);
                        // 设置bean字段的值
                        field.set(MapperParam, new Date(System.currentTimeMillis()));
                    }
                }
                // 如果注解是 FieldFill.INSERT的话
                if(TableFieldAnnotation.fill() == FieldFill.INSERT_UPDATE){
                    // 判断SQL语句是否是 INSERT 或者 UPDATE
                    if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
                        // 设置为true java不会去检查权限控制
                        field.setAccessible(true);
                        // 设置bean字段的值
                        field.set(MapperParam, new Date(System.currentTimeMillis()));
                    }
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}