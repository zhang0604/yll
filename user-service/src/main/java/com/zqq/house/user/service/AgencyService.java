package com.zqq.house.user.service;

import com.zqq.house.user.entity.Agency;
import com.zqq.house.user.mapper.AgencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/14
 * TIME: 16:09
 */
@Service
public class AgencyService {

    @Autowired
    private AgencyMapper agencyMapper;

    public List<Agency> getAllAgency() {
        return agencyMapper.getAllAgency();
    }
}
