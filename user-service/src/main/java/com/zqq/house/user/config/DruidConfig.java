package com.zqq.house.user.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created By 张庆庆
 * DATA: 2018/4/10
 * TIME: 11:18
 */
@Configuration
public class DruidConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.druid")
    public DruidDataSource druidDataSource(Filter filter){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setProxyFilters(Lists.newArrayList(filter()));
        return dataSource;
    }

    @Bean
    public Filter filter(){
        StatFilter filter = new StatFilter();
        filter.setSlowSqlMillis(500);
        filter.setLogSlowSql(true);
        return filter;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
    }
}
