package com.lm.admin.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lm.admin.common.ex.lthrow.UserExceptionThrow;
import com.lm.admin.common.r.UserResultEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 为什么要Springmvc接管呢？
 * 如果后续需要用统一配置文件或者统一配置中心去修改参数，就需要将类交给springmvc接管
 * 否则spring的注解无效
 */
@Component
public class JwtConfig {
    //jwt私钥
    private static final String KEY = "wenhaoccc";
    //指定作者
    private static final String AUTHOR = "lm";
    //指定token的key
    private static final String LM_USER_ID = "user_id";
    //1秒
    private static final  Long ONE_SECOND = 1000L;
    //1分钟
    private static final Long ONE_MINUTE = ONE_SECOND * 60;
    //1小时
    private static final Long ONE_HOUR = ONE_MINUTE * 60;
    //token 10小时过期
    private static final Long TOKEN_EXPIRE_TIME = ONE_HOUR * 18;

    /**
     * 生成秘钥
     * @param userId
     * @return
     */
    public String createToken(Long userId){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Algorithm algorithm = Algorithm.HMAC256(KEY);
        String token = JWT.create()
                .withIssuer(AUTHOR)
                .withClaim(LM_USER_ID,userId)
                .withIssuedAt(new Date(System.currentTimeMillis()))//设置签发日期
                .withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_EXPIRE_TIME))//设置过期日期
                .sign(algorithm);
        return token;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public boolean verify(String token){
        //等于空的时候
        if (!StringUtils.hasText(token)){
            return false;
        }
        try{
            Algorithm algorithm = Algorithm.HMAC256(KEY); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(AUTHOR)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            System.out.println("token失效了");
            return false;
        }
    }

    /**
     * 获取用户id
     * @param token
     * @return
     */
    public Long getTokenUserId(String token){
        //等于空的时候
        if (!StringUtils.hasText(token)){
            return null;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(KEY); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(AUTHOR)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(LM_USER_ID).asLong();
        }catch (Exception exception){
            throw new UserExceptionThrow(UserResultEnum.USER_TOKEN_ERROR);
        }
    }

    /**
     * 获取签发日期
     * @param token
     * @return
     */
    public Date getTokenIssuedTime(String token){
        try {
            // 1：确定token加密签名的算法和密钥
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            // 2 : 获取token的校验对象
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(AUTHOR)
                    .build(); //Reusable verifier instance
            // 3: 开始校验，如果校验通过DecodedJWT.如果token是伪造或者失效的，就会出现异常。
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getIssuedAt();
        } catch (Exception ex) {
            throw new UserExceptionThrow(UserResultEnum.USER_TOKEN_ERROR);
        }
    }

    /**
     * 获取过期时间
     * @param token
     * @return
     */
    public Date getTokenExpiresTime(String token){
        try {
            // 1：确定token加密签名的算法和密钥
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            // 2 : 获取token的校验对象
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(AUTHOR)
                    .build(); //Reusable verifier instance
            // 3: 开始校验，如果校验通过DecodedJWT.如果token是伪造或者失效的，就会出现异常。
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getIssuedAt();
        } catch (Exception ex) {
            throw new UserExceptionThrow(UserResultEnum.USER_TOKEN_ERROR);
        }
    }
}
