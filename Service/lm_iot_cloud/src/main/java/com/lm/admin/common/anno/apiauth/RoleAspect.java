package com.lm.admin.common.anno.apiauth;

import com.lm.admin.common.r.UserResultEnum;
import com.lm.admin.entity.bo.user.UserRoleBo;
import com.lm.admin.entity.dto.user.UserHeader;
import com.lm.admin.service.user.UserServiceImpl;
import com.lm.admin.utils.LmAssert;
import com.lm.admin.utils.lmthreadlocal.RoleThreadLocal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 角色验证注解实现
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor // 用于生成构造方法的 Lombok
public class RoleAspect {

    @Autowired
    private UserServiceImpl userService;


    @Pointcut("@annotation(com.lm.admin.common.anno.apiauth.RoleAuth)")
    public void poinitCut(){}

    @Before("poinitCut()")
    public void beforeAdvice(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取执行的方法
        Method method = signature.getMethod();
        // 获取方法的注解
        RoleAuth roleAuth = method.getAnnotation(RoleAuth.class);
        // 获取权限代号
        String codes = roleAuth.value();
        log.info("权限-->",codes.toString());
        // 获取前端请求的用户id
        UserHeader userHeader = RoleThreadLocal.get();
        // 查询前端用户的用户信息和角色信息
        UserRoleBo userById = userService.getUserById(userHeader.getUserId());
        // 校验注解中需要的角色
        LmAssert.isFalseEx(userById.getRoleCode().equals(codes), UserResultEnum.USER_ROLE_AUTH_EMPTY);
//        // 然后根据登录的用户去获取对应的权限信息
//        AdminUser adminUser = AdminUserThreadLocal.get();
//        // 查询用户所有的权限信息 追求实时
//        List<String> permissions = adminuserService.findByUserPermission(adminUser.getId());
//        // 用户输入
//        List<String> strings1 = Arrays.asList(codes);
//        //取交集
//        Collection intersection = CollectionUtils.intersection(permissions, strings1);
//        // 如果没有权限直接抛出异常
//        if(CollectionUtils.isEmpty(intersection)){
//            throw new ValidatorExceptionThrow(UserResultEnum.USER_ROLE_AUTH_EMPTY);
//        }
    }
}
