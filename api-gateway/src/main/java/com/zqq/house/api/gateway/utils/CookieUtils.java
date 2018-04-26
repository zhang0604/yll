package com.zqq.house.api.gateway.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created By 张庆庆
 * DATA: 2018/4/19
 * TIME: 15:35
 */

public class CookieUtils {

    public static void setCookie(HttpServletResponse response,String cookieName,String cookieValue,Integer maxAge){
        Cookie cookie = new Cookie(cookieName,cookieValue);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletResponse response,String cookieName){
        Cookie cookie = new Cookie(cookieName,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
