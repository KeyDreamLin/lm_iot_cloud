package com.lm.admin.entity.pojo.userowner;

/**
 * 这个是用于用户中间表
 * @author Lm
 * @date 2022/11/24 21:54
 */
public class UserOwner implements java.io.Serializable {
    // 用户的id
    private Long u_id;
    // 用户拥有的id 比如设备id  设备分组id 设备策略id
    private Long o_id;
}
