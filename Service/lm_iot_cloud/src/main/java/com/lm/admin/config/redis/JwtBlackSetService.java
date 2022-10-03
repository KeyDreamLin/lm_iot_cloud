package com.lm.admin.config.redis;

import com.lm.common.redis.adminkey.RedisAndHeaderKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 飞哥
 * @Title: 学相伴出品
 * @Description: 飞哥B站地址：https://space.bilibili.com/490711252
 * 记得关注和三连哦！
 * @Description: 我们有一个学习网站：https://www.kuangstudy.com
 * @date 2022/1/12$ 17:22$
 */
@Slf4j
@Service
public class JwtBlackSetService implements IJwtBlackService , RedisAndHeaderKey {

    @Autowired
    public StringRedisTemplate redisTemplate;//RedisTemplate 会乱码  StringRedisTemplate用这个api是一样的
    /**
     * 添加黑白名单
     * @param token
     */
    @Override
    public void addBlackList(String token) {
        log.info("添加token黑名单token:{}....", token);
    // 2: 同时黑名单的set中也添加一份
        if (!this.redisTemplate.opsForSet().isMember(REDIS_LOGIN_LOGOUT_BLACKLIST_KEY, token)) {
        this.redisTemplate.opsForSet().add(REDIS_LOGIN_LOGOUT_BLACKLIST_KEY, token);
    }
}


    // 2: 判断当前用户是否在黑名单中
    @Override
    public boolean isBlackList(String token) {
        boolean flag = false;
        try {
            flag = this.redisTemplate.opsForSet().isMember(REDIS_LOGIN_LOGOUT_BLACKLIST_KEY, token);
        } catch (Exception ex) {
            log.error("出现异常", ex.getMessage());
        }
        return flag;
    }

    // 4: 删除黑名单
    @Override
    public boolean removeBlackList(String token) {
        boolean flag = false;
        try {
            // 1：移除redis黑名单
            Long remove = this.redisTemplate.opsForSet().remove(REDIS_LOGIN_LOGOUT_BLACKLIST_KEY, token);
            // 2：移除数据库DB黑名单
            // todo
            flag = remove > 0;
        } catch (Exception ex) {
            log.error("出现异常", ex.getMessage());
        }
        return flag;
    }

}
