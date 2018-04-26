package com.zqq.house.api.gateway.interceptors;

import com.zqq.house.api.gateway.commom.CommonProperties;
import com.zqq.house.api.gateway.dao.UserDao;
import com.zqq.house.api.gateway.entity.User;
import com.zqq.house.api.gateway.utils.CookieUtils;
import com.zqq.house.api.gateway.utils.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created By 张庆庆
 * DATA: 2018/4/19
 * TIME: 15:49
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        UserThreadLocal.setUser(null);
        String loginUrl = "/accounts/signin";

        String referer = request.getHeader("Referer");
        String requestURI = request.getRequestURI();
        if (!InterceptorList.isContains(requestURI)){
            CookieUtils.deleteCookie(response,CommonProperties.REFERER_URL);
            return true;
        }
        //需要拦截的请求
        //获取 token
        Cookie cookie = WebUtils.getCookie(request, CommonProperties.USER_TOKEN);
        if (cookie == null || StringUtils.isEmpty(cookie.getValue())){
            //token 为空 重定向
            response.sendRedirect(loginUrl);
            CookieUtils.setCookie(response,CommonProperties.REFERER_URL,referer,1800);
            return false;
        }
        //token 不为空 鉴权
        String token = cookie.getValue();
        User userByToken = userDao.getUserByToken(token);
        if (null == userByToken){
            //登陆超时
            response.sendRedirect(loginUrl);
            CookieUtils.setCookie(response,CommonProperties.REFERER_URL,referer,1800);
            return false;
        }
        //登陆成功
        UserThreadLocal.setUser(userByToken);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
