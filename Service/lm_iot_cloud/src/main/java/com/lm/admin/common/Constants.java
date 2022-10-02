package com.lm.admin.common;

public class Constants {
    //用户的Session
    public static final String USERID_SESSION = "userIdSession";

    public static final String USER_TOKEN ="userToken";

    //token失效时间 分钟
    public static final int FAILURE_TIMER_M = 30;
    public static final int FAILURE_TIMER_S = (FAILURE_TIMER_M * 60);
    //token失效时间戳
    public static final Long FAILURE_TIMER_MS = (FAILURE_TIMER_M * 60 * 1000L);
}
