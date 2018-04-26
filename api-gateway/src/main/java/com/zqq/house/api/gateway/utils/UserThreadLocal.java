package com.zqq.house.api.gateway.utils;


import com.zqq.house.api.gateway.entity.User;

/**
 *
 * 存放user对象的本地线程对象
 *
 * Created By 张庆庆
 * DATA: 2018/4/11
 * TIME: 11:22
 */

public class UserThreadLocal {

    private static final ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUser(User user){
        USER_THREAD_LOCAL.set(user);
    }

    public static User getUser(){
        return USER_THREAD_LOCAL.get();
    }
}
