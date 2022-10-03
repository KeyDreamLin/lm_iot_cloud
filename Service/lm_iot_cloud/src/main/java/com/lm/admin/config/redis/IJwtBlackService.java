package com.lm.admin.config.redis;

/**
 * @author 飞哥
 * @Title: 学相伴出品
 * @Description: 飞哥B站地址：https://space.bilibili.com/490711252
 * 记得关注和三连哦！
 * @Description: 我们有一个学习网站：https://www.kuangstudy.com
 * @date 2022/1/12$ 22:56$
 */
public interface IJwtBlackService {


    //添加黑白名单
    void addBlackList(String token);

    // 2: 判断当前用户是否在黑名单中
    boolean isBlackList(String token);

    // 4: 删除黑名单
    boolean removeBlackList(String token);
}
