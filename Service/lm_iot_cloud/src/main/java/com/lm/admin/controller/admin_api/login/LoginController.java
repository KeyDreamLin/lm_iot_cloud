package com.lm.admin.controller.admin_api.login;

import com.lm.admin.common.r.AdminUserResultEnum;
import com.lm.admin.common.r.ServerErrorResultEnum;
import com.lm.admin.config.jwt.JwtConfig;
import com.lm.admin.config.redis.JwtBlackSetService;
import com.lm.admin.controller.admin_api.AdminBaseController;
import com.lm.admin.entity.bo.adminuser.AdminUserLoginBo;
import com.lm.admin.entity.pojo.adminuser.AdminUser;
import com.lm.admin.entity.vo.adminuser.AdminUserLoginVo;
import com.lm.admin.service.adminuser.AdminUserServiceImpl;
import com.lm.admin.tool.LmAssert;
import com.lm.common.redis.adminkey.RedisAndHeaderKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * /api/admin/**
 * @author Lm
 * @date 2022/10/3 20:19
 */
@Slf4j
@RestController
public class LoginController extends AdminBaseController implements RedisAndHeaderKey {
    // 管理员服务
    @Autowired
    private AdminUserServiceImpl adminUserService;

    @Autowired
    private JwtConfig jwtService;

    @Autowired
    private StringRedisTemplate redisTemplate;  // 操作Redis

    @Autowired
    private JwtBlackSetService jwtBlackSetService;

    /**
     * @description: 登录接口
     * @path : /api/admin/login
     * @author: Lm
     * @date: 2022/10/2 16:01
     * @param adminUserLoginVo
     * @return AdminUser
     **/
    @PostMapping("/login")
    @ResponseBody
        public AdminUserLoginBo login(@RequestBody AdminUserLoginVo adminUserLoginVo){
        log.info("UserController_Login--->用户登录-->账号：{}--密码：{}  验证码：{} 验证码UUID：{}"
                , adminUserLoginVo.getAccount()
                , adminUserLoginVo.getPassword()
                , adminUserLoginVo.getCode()
                , adminUserLoginVo.getCodeuuid()
        );
        // 断言类写，就是里面可以判断空、是否true、是否为false等等
        // 这个类可以抛出还可以顺便抛出异常，传入枚举，方便
        //------------------------------------------------------------
        // 校验回传过来数据是否为空，抛出异常
        LmAssert.isEmptyEx(adminUserLoginVo.getAccount(), AdminUserResultEnum.USER_INPUT_NULL_ERROR);
        LmAssert.isEmptyEx(adminUserLoginVo.getPassword(), AdminUserResultEnum.USER_INPUT_NULL_ERROR);
        LmAssert.isEmptyEx(adminUserLoginVo.getCode(), AdminUserResultEnum.USER_INPUT_NULL_ERROR);
        LmAssert.isEmptyEx(adminUserLoginVo.getCodeuuid(), AdminUserResultEnum.USER_INPUT_NULL_ERROR);

        // 1、明天接入前端 后 加入验证码判断
        // 先通过codeUuid取出对应的验证码
        String db_codeByUuid =  redisTemplate.opsForValue().get(REDIS_CODE_UUID_KEY+adminUserLoginVo.getCodeuuid());
        // 如果UUID查不到对应的验证码 那就 抛出 服务器异常
        LmAssert.isNotNull(db_codeByUuid, ServerErrorResultEnum.SERVER_ERROR);

        // 取出验证码后将数据库的删除掉 不然前端不刷新验证码 拿同一个UUID和验证码过来还是会判断成功
        redisTemplate.delete(REDIS_CODE_UUID_KEY+adminUserLoginVo.getCodeuuid());
        // 判断vo传过来的 验证码 是否和 数据库 查出来的一致
        boolean flagCheckCode = db_codeByUuid.equals(adminUserLoginVo.getCode());
        // 如果输入的 验证码 和 数据库 验证码不一致，抛出异常 验证码错误
        LmAssert.isFalseEx(flagCheckCode, AdminUserResultEnum.USER_INPUT_CODE_ERROR);


        // 先通过账号查询到对应用户的信息
        AdminUser db_adminUser = adminUserService.getAdminUserByAccout(adminUserLoginVo.getAccount());
        // 如果为空抛出异常
        LmAssert.isNotNull(db_adminUser, AdminUserResultEnum.USER_LOGIN_NO_EXIST);

        // 判断vo传过来的密码是否和数据库查出来的一致
        boolean flagCheckPwd = db_adminUser.getPassword().equals(adminUserLoginVo.getPassword());
        // TODO 2、明天接入前端 后把加密加上
        // 如果输入的密码和数据库密码不一致，抛出异常 登录失败
        LmAssert.isFalseEx(flagCheckPwd, AdminUserResultEnum.USER_LOGIN_NO_EXIST);

        // 创建Bo对象 返回数据到前端
        AdminUserLoginBo adminUserLoginBo = new AdminUserLoginBo();
        // 先屏蔽敏感数据
        db_adminUser.setPassword("");
        // 然后将用户信息放到Bo
        adminUserLoginBo.setUser(db_adminUser);
        // TODO 3、明天接入前端后 返回jwt 和 UUID
        return adminUserLoginBo;
    }
}
