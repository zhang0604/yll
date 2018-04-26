package com.zqq.house.api.gateway.confige;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created By 张庆庆
 * DATA: 2018/4/13
 * TIME: 14:54
 */
@ConfigurationProperties(prefix = "spring.httpclient")
public class HttpClientProperties {

    private Integer connectTimeOut = 1000;

    private Integer socketTimeOut = 1000000;

    private String agent = "agent";
    private Integer maxConnPerRoute = 10;
    private Integer maxConnTotal   = 50;

    public Integer getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(Integer connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public Integer getSocketTimeOut() {
        return socketTimeOut;
    }

    public void setSocketTimeOut(Integer socketTimeOut) {
        this.socketTimeOut = socketTimeOut;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Integer getMaxConnPerRoute() {
        return maxConnPerRoute;
    }

    public void setMaxConnPerRoute(Integer maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
    }

    public Integer getMaxConnTotal() {
        return maxConnTotal;
    }

    public void setMaxConnTotal(Integer maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
    }
}
