package com.lm.admin.filter;

import com.lm.admin.common.anno.login.IgnoreToken;
import com.lm.admin.common.r.UserResultEnum;
import com.lm.admin.config.jwt.JwtConfig;
import com.lm.admin.entity.bo.user.UserRoleBo;
import com.lm.admin.entity.dto.user.UserHeader;
import com.lm.admin.entity.pojo.roles.Roles;
import com.lm.admin.entity.pojo.user.User;
import com.lm.admin.mapper.mysql.roles.RolesMapper;
import com.lm.admin.service.roles.RolesServiceImpl;
import com.lm.admin.service.user.UserServiceImpl;
import com.lm.admin.utils.DateTool;
import com.lm.admin.utils.LmAssert;
import com.lm.admin.utils.lmthreadlocal.RoleThreadLocal;
import com.lm.common.redis.adminkey.RedisAndHeaderKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 拦截器 用户验证
 * @author Lm
 * @date 2022/11/22 9:41
 */
@Component
@Slf4j
public class UserAuth implements HandlerInterceptor{
    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RolesServiceImpl rolesService;

    @Autowired
    private UserServiceImpl userService;

    //用于在将请求发送到控制器之前执行操作。此方法应返回true，以将响应返回给客户端。
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //通过自定义注解去跳过token校验
        // handler从object对象转换成具体的目标对象HandlerMethod
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 获取执行的方法
        Method method = handlerMethod.getMethod();
        if (
            method.getAnnotation(IgnoreToken.class) != null ||
            handlerMethod.getBeanType().getAnnotation(IgnoreToken.class) != null
        ) {
            return true;
        }
        // --------------跳过验证的注解--------------


        // 获取请求头的数据
        // 获取jwt
        String token_Jj = request.getHeader(RedisAndHeaderKey.HEADER_TOKEN_JJ);
        // 如果jwt为空 抛出token异常
        LmAssert.isEmptyEx(token_Jj, UserResultEnum.USER_TOKEN_NOT_FOUND);
        // 如果jwt为过期 抛出token异常
        LmAssert.isFalseEx(jwtConfig.verify(token_Jj), UserResultEnum.USER_TOKEN_NOT_FOUND);

        // 获取到jwt里面的userId
        Long tokenUserId = jwtConfig.getTokenUserId(token_Jj);


        // 检查用户的状态  是否禁用
        UserRoleBo userById = userService.getUserById(tokenUserId);
        LmAssert.isFalseEx((userById.getStatus() == 1) ,UserResultEnum.USER_LOGIN_ACCOUNT_STATE_STOP_USE);


        // 通过jwt解析出来的用户id 查询出对应的角色信息
        Roles rolesByUserId = rolesService.getRolesByUserId(tokenUserId);

        UserHeader userHeader = new UserHeader();

        userHeader.setUserId(tokenUserId);
        userHeader.setUserRoleCode(rolesByUserId.getRoleCode());

        RoleThreadLocal.put(userHeader);
        log.info("jwt-->{}",token_Jj);


        // 获取token jwt的签发时间
        Date signTokenTime = jwtConfig.getTokenIssuedTime(token_Jj);
        // 获取token使用时间
        Long UseTokenDateMinute = DateTool.diffReturnMinute(new Date(),signTokenTime);
        log.info("token jwt的使用时间--->{}",UseTokenDateMinute);
        //使用时间超过了60分钟，就重新生成一串jwt返回给前端
        if (UseTokenDateMinute >= 60){
            // 续期，重新生成一个新的token
            String newToken = jwtConfig.createToken(Long.valueOf(tokenUserId));
            log.info("重新生成一个jwt--->{}",newToken);
            // 通过response的头部输出token,然后前台通过reponse获取
            response.setHeader("token_Jj", newToken);
        }

        return true;
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        RoleThreadLocal.remove();

    }

    //用完记得删除

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RoleThreadLocal.remove();
    }

}
