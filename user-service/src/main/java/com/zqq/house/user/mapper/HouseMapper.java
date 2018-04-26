package com.zqq.house.user.mapper;

import com.zqq.house.user.entity.House;
import com.zqq.house.user.page.PageParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/22
 * TIME: 16:47
 */

public interface HouseMapper {

    /**
     * 条件分页查询
     * @param house
     * @param pageParams
     * @return
     */
    List<House> selectPageHouses(@Param("house")House house, @Param("params")PageParams pageParams);

    /**
     * 新增
     * @param house
     * @return
     */
    int insert(House house);

    /**
     * 查询房产总记录数
     * @return
     */
    Long selectHouseCount();


}
