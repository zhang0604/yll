package com.zqq.house.user.utils;

import com.zqq.house.user.page.PageData;
import com.zqq.house.user.page.Pagination;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/20
 * TIME: 10:06
 */

public class PageUtil {

    public static  <T> PageData<T> buildPage(List<T> list, Long count,
                                             Integer pageSize, Integer pageNum){
        Pagination _pagination = new Pagination(pageSize, pageNum, count);
        return new PageData<>(_pagination, list);
    }
}
