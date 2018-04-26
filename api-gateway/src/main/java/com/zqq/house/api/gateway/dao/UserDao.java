package com.zqq.house.api.gateway.dao;

import com.zqq.house.api.gateway.confige.GenericRest;
import com.zqq.house.api.gateway.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import com.zqq.house.api.gateway.commom.RestResponse;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/13
 * TIME: 16:33
 */
@Repository
public class UserDao {

    @Autowired
    private GenericRest genericRest;

    @Value("${spring.user.service}")
    private String USER_SERVICE_URL;

    /**
     * 执行 用户注册
     * @param user
     * @return
     */
    public Boolean doRegister(User user){
        String url = USER_SERVICE_URL + "user/add";
        RestResponse<String> body = genericRest.post(url, user, new ParameterizedTypeReference<RestResponse<String>>() {
        }).getBody();
        return body.getCode() == 0;
    }


    /**
     * 调用接口服务激活
     * @param user
     * @return
     */
    public Boolean update(User user) {
        user.setEnable(1);
        String url = USER_SERVICE_URL + "user/update";
        RestResponse<String> response = genericRest.post(url, user, new ParameterizedTypeReference<RestResponse<String>>() {
        }).getBody();
        return response.getCode() == 0;
    }

    /**
     * 调用接口服务 删除 未激活的用户
     * @param email
     */
    public void delete(String email) {
        String url = USER_SERVICE_URL + "user/deleteByEmail";
        genericRest.post(url, email, new ParameterizedTypeReference<RestResponse<String>>() {
        }).getBody();
    }

    public User doLogin(String email, String password) {

        User user = new User();
        user.setEnable(1);
        user.setEmail(email);
        user.setPasswd(password);

        String url = USER_SERVICE_URL + "user/auth";
        RestResponse<User> response = genericRest.post(url, user, new ParameterizedTypeReference<RestResponse<User>>() {
        }).getBody();
        return response.getResult();
    }


    /**
     * 根据token查询 用户信息
     * @param token
     * @return
     */
    public User getUserByToken(String token){
        String url = USER_SERVICE_URL + "user/get?token=" + token;
        RestResponse<User> body = genericRest.get(url, new ParameterizedTypeReference<RestResponse<User>>() {
        }).getBody();
        if (null == body){
            return null;
        }
        return body.getResult();
    }

    /**
     * 调用服务接口实现登出操作
     * @param token
     */
    public void logout(String token) {
        String url = USER_SERVICE_URL + "/user/delete?token=" +token;
        genericRest.get(url, new ParameterizedTypeReference<Object>() {
        });
    }


    public User queryByWhere(User user) {
        String url = USER_SERVICE_URL +"user/getList";
        RestResponse<List<User>> response = genericRest.post(url, user, new ParameterizedTypeReference<RestResponse<List<User>>>() {
        }).getBody();
        if (response.getCode() == 0){
            return response.getResult().isEmpty()? null : response.getResult().get(0);
        }
        return null;
    }
}
