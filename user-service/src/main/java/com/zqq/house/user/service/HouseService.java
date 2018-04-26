package com.zqq.house.user.service;

import com.zqq.house.user.entity.House;
import com.zqq.house.user.mapper.CommunityMapper;
import com.zqq.house.user.mapper.HouseMapper;
import com.zqq.house.user.mapper.HouseUserMapper;
import com.zqq.house.user.page.PageData;
import com.zqq.house.user.page.PageParams;
import com.zqq.house.user.utils.String2List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By 张庆庆
 * DATA: 2018/4/22
 * TIME: 17:29
 */
@Service
public class HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private HouseUserMapper houseUserMapper;

    @Autowired
    private CommunityMapper communityMapper;

    @Value("${spring.image.url}")
    private String imgPrefix;

    public PageData<House> selectPageHouses(House house,Integer pageNum,Integer pageSize){
        return null;
    }


    /**
     * 条件查询数据并处理图片前缀问题
     * @param house
     * @param pageParams
     * @return
     */
    public List<House> houseList(House house, PageParams pageParams){
        List<House> houses = houseMapper.selectPageHouses(house, pageParams);
        houses.forEach(h -> {
            h.setFirstImg(imgPrefix+h.getFirstImg());
            h.setFeatureList(String2List.toList(h.getProperties()));
            h.setImageList(h.getImageList().stream().map(img->imgPrefix+img).collect(Collectors.toList()));
            h.setFloorPlanList(String2List.toList(h.getFloorPlan()).stream().map(pic->imgPrefix+pic).collect(Collectors.toList()));
        });
        return houses;
    }


}
