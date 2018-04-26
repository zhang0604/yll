package com.zqq.house.api.gateway.service;

import com.zqq.house.api.gateway.dao.AgencyDao;
import com.zqq.house.api.gateway.entity.Agency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/14
 * TIME: 15:45
 */
@Service
public class AgencyService {

    @Autowired
    private AgencyDao agencyDao;


    public List<Agency> selectAgency() {
        List<Agency> agencyList = agencyDao.getAgencyList();
        return agencyList;
    }
}
