package com.lm.admin.config.redis;

import com.alibaba.fastjson2.support.spring.data.redis.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * 使用fastjson2作为bean的序列化和反序列化
     * @param redisConnectionFactory
     * @return
     */
    @Bean("fastjson2RedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 官方文档cv的
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setDefaultSerializer(fastJsonRedisSerializer);//设置默认的Serialize，包含 keySerializer & valueSerializer

        // 将key设置为字符串 这个自己加
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //redisTemplate.setKeySerializer(fastJsonRedisSerializer);//单独设置keySerializer
        //redisTemplate.setValueSerializer(fastJsonRedisSerializer);//单独设置valueSerializer

        return redisTemplate;
    }
}