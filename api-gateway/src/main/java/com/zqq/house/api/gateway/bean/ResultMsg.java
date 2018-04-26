package com.zqq.house.api.gateway.bean;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created By 张庆庆
 * DATA: 2018/4/10
 * TIME: 16:19
 */

public class ResultMsg {

    public static final String ERROR_MSG_KEY = "errorMsg";

    public static final String SUCCESS_MSG_KEY = "successMsg";

    private String errorMsg;

    private String successMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public static ResultMsg error(String msg){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setErrorMsg(msg);
        return resultMsg;
    }

    public static ResultMsg succss(String msg){
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccessMsg(msg);
        return resultMsg;
    }

    public Boolean isSuccess(){
        return StringUtils.isBlank(errorMsg);
    }

    public Map<String,String> asMap(){
        Map<String,String> map = Maps.newHashMap();
        map.put(ERROR_MSG_KEY,errorMsg);
        map.put(SUCCESS_MSG_KEY,successMsg);
        return map;
    }

    public String asUrlParams(){
        Map<String,String > map = asMap();
        Map<String,String> newMap = Maps.newHashMap();
        map.forEach((k,v) -> {if(StringUtils.isNotEmpty(v))
            try {
                newMap.put(k, URLEncoder.encode(v,"utf-8"));
            } catch (UnsupportedEncodingException e) {

            }});
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
    }


}
