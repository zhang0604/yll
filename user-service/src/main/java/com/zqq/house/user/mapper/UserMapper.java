package com.zqq.house.user.mapper;

import com.zqq.house.user.entity.User;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/13
 * TIME: 22:39
 */

public interface UserMapper {

    /**
     * 查询所有用户信息
     * @return
     */
    List<User> selectUsers();

    /**
     * 条件查询
     * @param user
     * @return
     */
    List<User> selectUsersByQuery(User user);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    int insert(User user);

    /**
     * 删除用户信息
     * @param email
     * @return
     */
    int delete(String email);

    /**
     * 非空更新
     * @param user
     * @return
     */
    int update(User user);
}
