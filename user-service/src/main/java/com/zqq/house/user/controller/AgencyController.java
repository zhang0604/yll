package com.zqq.house.user.controller;

import com.zqq.house.user.common.RestResponse;
import com.zqq.house.user.entity.Agency;
import com.zqq.house.user.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/14
 * TIME: 16:06
 */
@RestController
@RequestMapping("agency")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @RequestMapping(value = "getAllAgency",method = RequestMethod.GET)
    public RestResponse<List<Agency>> getAllAgency(){
        List<Agency> agencyList = agencyService.getAllAgency();
        return RestResponse.success(agencyList);
    }

}
