package com.zqq.house.api.gateway.confige;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.httpclient.LogbookHttpRequestInterceptor;
import org.zalando.logbook.httpclient.LogbookHttpResponseInterceptor;

/**
 * Created By 张庆庆
 * DATA: 2018/4/13
 * TIME: 14:56
 */
@Configuration
@ConditionalOnClass(HttpClient.class)
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {

    private final HttpClientProperties httpClientProperties;


    public HttpClientAutoConfiguration(HttpClientProperties httpClientProperties) {
        this.httpClientProperties = httpClientProperties;
    }

    @Autowired
    private LogbookHttpRequestInterceptor requestInterceptor;

    @Autowired
    private LogbookHttpResponseInterceptor responseInterceptor;


    @Bean
    @ConditionalOnMissingBean(HttpClient.class)
    public HttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(httpClientProperties.getConnectTimeOut())
                .setSocketTimeout(httpClientProperties.getSocketTimeOut()).build();// 构建requestConfig
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
                .setUserAgent(httpClientProperties.getAgent())
                .setMaxConnPerRoute(httpClientProperties.getMaxConnPerRoute())
                .setMaxConnTotal(httpClientProperties.getMaxConnTotal())
                .addInterceptorFirst(requestInterceptor)
                .addInterceptorFirst(responseInterceptor)
                .build();
        return client;
    }



}
