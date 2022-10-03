package com.lm.common.redis.adminkey;

public interface RedisAndHeaderKey {
    // 用于获取header请求头中的token_jwt  用于登录校验接口使用
    String HEADER_TOKEN_JJ = "token_jj";
    // 用于获取header请求头中的token_user_id 用于接口校验和下线使用
    String HEADER_TOKEN_USER_ID = "token_user_id";
    // 用于获取header请求头中的token_uuid 用于校验下线使用
    String HEADER_TOKEN_UUID = "token_uuid";

    // 用于登录使用的key，将id作为key把登录后生成的UUID放到Redis
    String REDIS_LOGIN_UUID_KEY = "lm:user:login:id:";
    // 用于注销的功能，用户注销后将jwt放入Redis，登录拦截器校验黑名单
    String REDIS_LOGIN_LOGOUT_BLACKLIST_KEY = "lm:user:logout:blacklist:";
}
