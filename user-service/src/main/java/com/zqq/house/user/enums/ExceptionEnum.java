package com.zqq.house.user.enums;

/**
 * Created By 张庆庆
 * DATA: 2018/4/13
 * TIME: 20:39
 */

public enum ExceptionEnum {


    ILLEGAL_PARAM(9001,"参数非法"),
    UNKNOW_EXCEPTION(500,"未知错误"),
    USER_NOT_EXIST(9002,"用户不存在"),
    NOT_FOUND(9003,"未找到用户信息"),
    NOT_LOGIN(9004,"未登录或登录超时");

    private Integer code;

    private String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
