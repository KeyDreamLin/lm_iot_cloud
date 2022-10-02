package com.lm.admin.common.ex.handler;


import com.lm.admin.common.ex.ErrorHandler;
import com.lm.admin.common.ex.lthrow.UserExceptionThrow;
import com.lm.admin.common.ex.lthrow.ValidatorExceptionThrow;
import com.lm.admin.common.r.ServerErrorResultEnum;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常管理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局运行异常捕获
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public ErrorHandler makeException(Throwable e, HttpServletRequest request) {
        // 1: 一定要加下面这行代码。打印异常堆栈信息，方便程序员去根据异常排查错误 --服务开发者
        e.printStackTrace();
        // 2: 出现异常，统一返回固定的状态---服务用户
        ErrorHandler errorHandler =  ErrorHandler.error(
                ServerErrorResultEnum.SERVER_ERROR.getCode(),
                ServerErrorResultEnum.SERVER_ERROR.getMsg(),e);
        // 3: 最后返回
        return  errorHandler;
    }

    /**
     * 全局运行异常捕获
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(UserExceptionThrow.class)
    public ErrorHandler UserException(UserExceptionThrow e, HttpServletRequest request) {
        // 1: 一定要加下面这行代码。打印异常堆栈信息，方便程序员去根据异常排查错误 --服务开发者
        //自定义的异常，看情况加下面这行代码也行
        e.printStackTrace();
        // 2: 出现异常，统一返回固定的状态---服务用户
        ErrorHandler errorHandler =  ErrorHandler.error(
                e.getCode(),
                e.getMsg(),e);
        // 3: 最后返回
        return  errorHandler;
    }

    /**
     * 自定义异常类捕获 自定义异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(ValidatorExceptionThrow.class)
    public ErrorHandler ValidatorException(ValidatorExceptionThrow e, HttpServletRequest request) {
        // 1: 一定要加下面这行代码。打印异常堆栈信息，方便程序员去根据异常排查错误 --服务开发者
        //自定义的异常，看情况加下面这行代码也行
        e.printStackTrace();
        // 2: 出现异常，统一返回固定的状态---服务用户
        ErrorHandler errorHandler =  ErrorHandler.error(
                e.getCode(),
                e.getMsg(),e);
        // 3: 最后返回
        return  errorHandler;
    }

    /**
     * 捕获统一验证器的异常 starter-validation
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorHandler ValidationException(MethodArgumentNotValidException e , HttpServletRequest request){
        // 1: 一定要加下面这行代码。打印异常堆栈信息，方便程序员去根据异常排查错误 --服务开发者
        //自定义的异常，看情况加下面这行代码也行
        e.printStackTrace();

        // 获取统一异常的错误信息列表
        List<FieldError> fieldErrors =  e.getFieldErrors();
        // 错误信息存到这里 返回到页面
        List<Map<String,String>> ReterrorLst = new ArrayList<>();

        fieldErrors.forEach(item->{
            // 过滤器
            Long count = ReterrorLst.stream().filter(map->map.get("field").equals(item.getField())).count();
            // 一个变量只显示一个异常信息 不然就会出现一个变量有多个信息
            if (count == 0L){
                Map<String,String> errorMesMap = new HashMap<>();
                errorMesMap.put("msg",item.getDefaultMessage());
                errorMesMap.put("field",item.getField());
                ReterrorLst.add(errorMesMap);
            }
        });
        // 2: 出现异常，统一返回固定的状态---服务用户
        ErrorHandler errorHandler =  ErrorHandler.error(
                4000,
                ReterrorLst,
                e.getClass().getName());
        // 3: 最后返回
        return  errorHandler;
    }
}
