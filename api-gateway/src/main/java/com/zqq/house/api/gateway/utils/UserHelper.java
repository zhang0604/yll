package com.zqq.house.api.gateway.utils;

import com.zqq.house.api.gateway.bean.ResultMsg;
import com.zqq.house.api.gateway.entity.User;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * 用户验证工具类
 *
 * Created By 张庆庆
 * DATA: 2018/4/10
 * TIME: 16:41
 */

public class UserHelper {


    public static ResultMsg valiate(User user){
        if (StringUtils.isBlank(user.getEmail())){
            return ResultMsg.error("邮箱不能为空");
        }

        if (StringUtils.isEmpty(user.getName())){
            return ResultMsg.error("用户名不能为空");
        }

        if (user.getPasswd().length()<6){
            return ResultMsg.error("密码必须大于六位");
        }
        //成功返回空串
        return ResultMsg.succss("");
    }
}
