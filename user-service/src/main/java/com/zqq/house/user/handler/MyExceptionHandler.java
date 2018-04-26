package com.zqq.house.user.handler;

import com.zqq.house.user.common.RestResponse;
import com.zqq.house.user.enums.ExceptionEnum;
import com.zqq.house.user.myexception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * 异常的统一处理
 *
 * Created By 张庆庆
 * DATA: 2018/4/13
 * TIME: 19:32
 */
@ControllerAdvice
public class MyExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public RestResponse<Object> exceptionHandle(Throwable throwable){

        if (throwable instanceof MyException){
            //自定义异常,强转
            MyException exception = (MyException) throwable;
            return exception.callback(exception);
        }else{
            //系统异常
            LOGGER.error("##异常信息##"+throwable.getMessage());
            return RestResponse.exception(ExceptionEnum.UNKNOW_EXCEPTION.getCode(),ExceptionEnum.UNKNOW_EXCEPTION.getMessage());
        }
    }
}
