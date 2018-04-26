package com.zqq.house.user.controller;

import com.zqq.house.user.common.RestResponse;
import com.zqq.house.user.entity.User;
import com.zqq.house.user.enums.ExceptionEnum;
import com.zqq.house.user.myexception.UserException;
import com.zqq.house.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created By 张庆庆
 * DATA: 2018/4/13
 * TIME: 16:30
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    //-------------------------查询用户信息---------------------------------
    @RequestMapping(value = "queryUser",method = RequestMethod.GET)
    public RestResponse<User> getUser(@RequestParam("id")Long id){
        User user = userService.queryUserById(id);
        if (user == null){
            throw new UserException(ExceptionEnum.NOT_FOUND);
        }
        return RestResponse.success(user);
    }

    /**
     * 账号密码 查询用户信息
     * @param email
     * @param passwd
     * @return
     */
    @RequestMapping("getUser")
    public RestResponse<User> getUser(@RequestParam("email")String email,
                                      @RequestParam("passwd")String passwd){
        User user = userService.getUser(email,passwd);
        if (null == user){
            LOGGER.info("账号密码 查询用户信息,未找到用户");
            throw new UserException(ExceptionEnum.NOT_FOUND);
        }
        return RestResponse.success(user);
    }

    /**
     * 根据条件查询用户列表
     * @param user
     * @return
     */
    @RequestMapping("getList")
    public RestResponse<List<User>> getList(@RequestBody User user){
        List<User> userList = userService.selectUsersByQuery(user);
        return RestResponse.success(userList);
    }


    //-------------------------用户注册&激活--------------------------------

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public RestResponse<String> add(@RequestBody User user){
        boolean register = userService.register(user);
        if (!register){
            throw new UserException(ExceptionEnum.ILLEGAL_PARAM);
        }
        return RestResponse.success();
    }

    @RequestMapping("update")
    public RestResponse<String> update(@RequestBody User user){
        Boolean flag = userService.update(user);
        if (!flag){
            throw new UserException(ExceptionEnum.ILLEGAL_PARAM);
        }
        return RestResponse.success();
    }


    @RequestMapping("deleteByEmail")
    public RestResponse<String> deleteByEmail(@RequestParam("email")String email){
        Boolean boo = userService.deleteByEmail(email);
        if (!boo){
            throw new UserException(ExceptionEnum.ILLEGAL_PARAM);
        }
        return RestResponse.success();
    }

    //-------------------------用户登录&&鉴权-------------------------------

    /**
     * 用户登陆
     * @param user
     * @return
     */
    @RequestMapping(value = "auth",method = RequestMethod.POST)
    public RestResponse<User> login(@RequestBody User user){
        User userInfo = userService.doLogin(user.getEmail(),user.getPasswd());
        return RestResponse.success(userInfo);
    }

    /**
     * 根据 token 鉴权
     * @param token
     * @return
     */
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public RestResponse<User> get(@RequestParam("token")String token){
        User userInfo = userService.verify(token);
        if (null == userInfo){
            throw new UserException(ExceptionEnum.NOT_LOGIN);
        }
        return RestResponse.success(userInfo);
    }

    //--------------------------登出操作-----------------------------------

    /**
     * 登出操作
     * @param token
     * @return
     */
    @RequestMapping("delete")
    public RestResponse<String> logOut(@RequestParam("token")String token){
        Boolean delete = userService.delete(token);
        if (!delete){
            throw new UserException(ExceptionEnum.NOT_LOGIN);
        }
        return RestResponse.success();
    }
}
