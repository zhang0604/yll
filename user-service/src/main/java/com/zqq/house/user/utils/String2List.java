package com.zqq.house.user.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/20
 * TIME: 22:24
 */

public class String2List {

    public static List<String> toList(String str){
        List<String> list = Lists.newArrayList();
        if (null == str){
            return list;
        }
        if (str.contains(",")){
            list = Splitter.on(",").splitToList(str);
            return list;
        }
        list.add(str);
        return list;
    }
}
