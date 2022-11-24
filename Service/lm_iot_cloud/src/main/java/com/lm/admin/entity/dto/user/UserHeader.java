package com.lm.admin.entity.dto.user;

import com.lm.common.redis.adminkey.RedisAndHeaderKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用于存储请求头中的用户id和角色代码
 * @author Lm
 * @date 2022/11/22 14:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHeader implements Serializable {
    // 用户id
    private Long userId;
    // 角色代码
    private String userRoleCode ;
}
