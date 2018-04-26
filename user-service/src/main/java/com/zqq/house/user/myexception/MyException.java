package com.zqq.house.user.myexception;

import com.zqq.house.user.common.RestResponse;

/**
 * Created By 张庆庆
 * DATA: 2018/4/13
 * TIME: 19:28
 */

public interface MyException<E,T> {

    /**
     * 回调函数
     * @param t
     * @return
     */
    RestResponse<E> callback(T t);

}
