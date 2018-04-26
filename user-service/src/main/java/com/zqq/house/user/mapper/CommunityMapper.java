package com.zqq.house.user.mapper;

import com.zqq.house.user.entity.Community;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/22
 * TIME: 17:04
 */

public interface CommunityMapper {

    List<Community> selectCommunityByWhere(Community community);
}
