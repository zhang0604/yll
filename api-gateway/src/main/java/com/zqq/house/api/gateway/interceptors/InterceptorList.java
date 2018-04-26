package com.zqq.house.api.gateway.interceptors;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/19
 * TIME: 15:52
 */

public class InterceptorList {

    public static List<String> list = Lists.newArrayList();

    static {
        list.add("profile");
        list.add("logout");
    }

    public static Boolean isContains(String requestURI){
        for(String url : list){
            if (requestURI.contains(url)){
                return true;
            }
        }
        return false;
    }
}
