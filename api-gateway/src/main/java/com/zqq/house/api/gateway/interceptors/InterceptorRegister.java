package com.zqq.house.api.gateway.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created By 张庆庆
 * DATA: 2018/4/19
 * TIME: 16:22
 */

@Configuration
public class InterceptorRegister implements WebMvcConfigurer {


    @Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
    }
}
