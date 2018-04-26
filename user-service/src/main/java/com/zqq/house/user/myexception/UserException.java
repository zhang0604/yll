package com.zqq.house.user.myexception;

import com.zqq.house.user.common.RestResponse;
import com.zqq.house.user.enums.ExceptionEnum;

/**
 *
 *  自定义 参数非法异常类
 *
 * Created By 张庆庆
 * DATA: 2018/4/13
 * TIME: 19:27
 */

public class UserException extends RuntimeException implements MyException<String,UserException> {

    private Integer code;

    private String message;

    public UserException(){}

    public UserException(ExceptionEnum exceptionEnum){
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public RestResponse<String> callback(UserException e) {
        RestResponse<String> restResponse = new RestResponse<>();
        restResponse.setCode(e.getCode());
        restResponse.setMsg(e.getMessage());
        restResponse.setResult("null");
        return restResponse;
    }
}
