package com.zqq.house.user.mapper;

import com.zqq.house.user.entity.HouseUser;
import com.zqq.house.user.entity.UserMsg;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/22
 * TIME: 17:11
 */

public interface HouseUserMapper {

    /**
     * 条件查询 house-user关联关系
     * @param houseUser
     * @return
     */
    List<HouseUser> selectHouseUserByWhere(HouseUser houseUser);

    /**
     * 新增 房产-用户关联关系
     * @param houseUser
     * @return
     */
    int insertMapping(HouseUser houseUser);

    /**
     * 更新house_user表(收藏&&取消收藏)
     * @param houseUser
     * @return
     */
    int update(HouseUser houseUser);


    /**
     * 插入留言信息
     * @param userMsg
     * @return
     */
    int insertMsg(UserMsg userMsg);
}
