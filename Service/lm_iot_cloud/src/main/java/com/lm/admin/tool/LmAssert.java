package com.lm.admin.tool;

import com.lm.admin.common.ex.lthrow.ValidatorExceptionThrow;
import com.lm.admin.common.r.GlobalResultEnumInterface;
import org.springframework.lang.NonNull;

public class LmAssert {
    /**
     * 字符串等于空的时候就抛出异常
     * 异常类型为ValidatorExceptionThrow验证器抛出异常
     * @param str 字符串
     * @param globalResultEnumInterface 传入你要抛出的异常信息枚举
     */
    public static void isEmptyEx(@NonNull String str, GlobalResultEnumInterface globalResultEnumInterface){
        if (str == null || "".equals(str)){
            throw new ValidatorExceptionThrow(globalResultEnumInterface);
        }
    }

    /**
     * 对象为空的时候抛出异常，封箱后的数据类型未赋值也会是null
     * 异常类型为ValidatorExceptionThrow验证器抛出异常
     * @param obj 对象
     * @param globalResultEnumInterface 传入你要抛出的异常信息枚举
     */
    public static void isNotNull(@NonNull Object obj ,GlobalResultEnumInterface globalResultEnumInterface){
        if (obj == null){
            throw new ValidatorExceptionThrow(globalResultEnumInterface);
        }
    }

    /**
     * 是true的时候就抛出异常
     * 异常类型为ValidatorExceptionThrow验证器抛出异常
     * @param isFlag 传入bool
     * @param globalResultEnumInterface 传入你要抛出的异常信息枚举
     */
    public static void isTrueEx(boolean isFlag,GlobalResultEnumInterface globalResultEnumInterface){
        if (isFlag == true){
            throw new ValidatorExceptionThrow(globalResultEnumInterface);
        }
    }

    /**
     * 是false的时候就抛出异常
     * 异常类型为ValidatorExceptionThrow验证器抛出异常
     * @param isFlag 传入bool
     * @param globalResultEnumInterface 传入你要抛出的异常信息枚举
     */
    public static void isFalseEx(boolean isFlag,GlobalResultEnumInterface globalResultEnumInterface){
        if (isFlag == false){
            throw new ValidatorExceptionThrow(globalResultEnumInterface);
        }
    }

    /**
     * 如果字符串是null或者""返回true
     *
     * @param value
     * @return
     **/
    public static boolean isEmpty(String value) {
        return null == value || "" .equals(value);
    }

    /**
     * 如果不是null或者""返回true
     *
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

}
