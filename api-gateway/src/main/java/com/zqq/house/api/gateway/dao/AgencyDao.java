package com.zqq.house.api.gateway.dao;

import com.zqq.house.api.gateway.commom.RestResponse;
import com.zqq.house.api.gateway.confige.GenericRest;
import com.zqq.house.api.gateway.entity.Agency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/14
 * TIME: 16:01
 */
@Repository
public class AgencyDao {

    @Autowired
    private GenericRest genericRest;

    @Value("${spring.user.service}")
    private String USER_SERVICE_URL;


    /**
     * 获取 机构列表
     * @return
     */
    public List<Agency> getAgencyList() {
        String url = USER_SERVICE_URL + "agency/getAllAgency";
        RestResponse<List<Agency>> body = genericRest.get(url, new ParameterizedTypeReference<RestResponse<List<Agency>>>() {
        }).getBody();
        return body.getResult();
    }
}
