package com.lm.admin.controller.user_api.login;

import com.lm.admin.common.r.ServerErrorResultEnum;
import com.lm.admin.common.r.UserResultEnum;
import com.lm.admin.config.jwt.JwtConfig;
import com.lm.admin.controller.user_api.UserBaseController;
import com.lm.admin.entity.bo.user.UserLoginBo;
import com.lm.admin.entity.pojo.roles.Roles;
import com.lm.admin.entity.pojo.user.User;
import com.lm.admin.entity.vo.user.UserLoginVo;
import com.lm.admin.service.roles.RolesServiceImpl;
import com.lm.admin.service.user.UserServiceImpl;
import com.lm.admin.utils.LmAssert;
import com.lm.admin.utils.pwd.DesUtils;
import com.lm.admin.utils.pwd.MD5Util;
import com.lm.common.redis.adminkey.RedisAndHeaderKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户api
 * /api/user/**
 * @author Lm
 * @date 2022/11/21 15:19
 *
 */
@Slf4j
@RestController
public class LoginController extends UserBaseController implements RedisAndHeaderKey {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RolesServiceImpl rolesService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private JwtConfig jwtConfig;
    /**
     * 登录接口
     * @param userLoginVo
     * @api: /api/user/login
     * @return
     */
    @PostMapping("/login")
    public UserLoginBo login(@RequestBody UserLoginVo userLoginVo){
        log.info("UserController_Login--->用户登录-->账号：{}--密码：{}  验证码：{} 验证码UUID：{}"
                , userLoginVo.getAccount()
                , userLoginVo.getPassword()
                , userLoginVo.getCode()
                , userLoginVo.getCodeuuid()
        );
        // 断言类写，就是里面可以判断空、是否true、是否为false等等
        // 这个类可以抛出还可以顺便抛出异常，传入枚举，方便
        //------------------------------------------------------------
        // 校验回传过来数据是否为空，抛出异常
        LmAssert.isEmptyEx(userLoginVo.getAccount(), UserResultEnum.USER_INPUT_NULL_ERROR);
        LmAssert.isEmptyEx(userLoginVo.getPassword(), UserResultEnum.USER_INPUT_NULL_ERROR);
        LmAssert.isEmptyEx(userLoginVo.getCode(), UserResultEnum.USER_INPUT_NULL_ERROR);
        LmAssert.isEmptyEx(userLoginVo.getCodeuuid(), UserResultEnum.USER_INPUT_NULL_ERROR);

        // 1、判断验证码是否为正确
        // 先通过codeUuid取出对应的验证码
        String db_codeByUuid =  redisTemplate.opsForValue().get(REDIS_CODE_UUID_KEY+userLoginVo.getCodeuuid());
        // 如果UUID查不到对应的验证码 那就 抛出 服务器异常
        LmAssert.isNotNull(db_codeByUuid, ServerErrorResultEnum.SERVER_ERROR);
        // 将取出后的验证码和验证码uuid删除
        redisTemplate.delete(REDIS_CODE_UUID_KEY+userLoginVo.getCodeuuid());
        // 判断验证码是否 输入正确
        LmAssert.isFalseEx(db_codeByUuid.equals(userLoginVo.getCode()),UserResultEnum.USER_INPUT_CODE_ERROR);

        // 前端密码会进行DES加密 然后后端解密DES后Md5加密存入数据库

        // 将前端回调的Des加密 密码 解密
        userLoginVo.setPassword(DesUtils.decrypt(userLoginVo.getPassword()));
        // 然后存进数据库的是md5加密
        userLoginVo.setPassword(MD5Util.strToMd5s(userLoginVo.getPassword()));

        // 2、通过账号获取用户的信息 判断账号是否存在、密码是否匹配
        User db_user = userService.getUserByAccount(userLoginVo.getAccount());
        // 如果通过账号查询不出用户信息 null 就抛出异常
        LmAssert.isNotNull(db_user,UserResultEnum.USER_LOGIN_NO_EXIST);

        // 判断前端传过来的密码 是否和 数据库查询出来的密码一样
        LmAssert.isFalseEx(db_user.getPassword().equals(userLoginVo.getPassword()),UserResultEnum.USER_LOGIN_NO_EXIST);
        // 判断账号是否被禁用
        LmAssert.isFalseEx((db_user.getStatus() == 1) ,UserResultEnum.USER_LOGIN_ACCOUNT_STATE_STOP_USE);




        // 3、查询出对应的角色信息
        Roles rolesByUserId = rolesService.getRolesByUserId(db_user.getId());
        // 如果查询不出对应的角色信息 抛出异常 服务器错误
        LmAssert.isNotNull(rolesByUserId, ServerErrorResultEnum.SERVER_ERROR);



        // 返回登录成功的信息
        UserLoginBo userLoginBo = new UserLoginBo();
        // 覆盖敏感信息
        db_user.setPassword("");
        // 传入用户信息
        userLoginBo.setUser(db_user);
        // 传入用户角色code
        userLoginBo.setRoleCode(rolesByUserId.getRoleCode());

        // 根据用户id生成jwt秘钥 后续携带头就行
        userLoginBo.setTokenJj(jwtConfig.createToken(db_user.getId()));

        return userLoginBo;
    }
}
