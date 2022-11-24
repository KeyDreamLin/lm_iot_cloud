package com.lm.admin.utils.lmthreadlocal;

import com.lm.admin.entity.dto.user.UserHeader;

/**
 * 线程共享 角色信息
 * @author Lm
 * @date 2022/11/22 14:52
 */
public class RoleThreadLocal {
    //每次使用完记得删除！不然会泄露
    // 实现一个threadlocal线程副本
    static ThreadLocal<UserHeader> roleThreadLocal = new ThreadLocal<>();

    // 添加
    public static void put(UserHeader userHeader) {
        roleThreadLocal.set(userHeader);
    }

    // 获取
    public static UserHeader get() {
        return roleThreadLocal.get();
    }

    // 删除
    public static void remove() {
        roleThreadLocal.remove();
    }
}
