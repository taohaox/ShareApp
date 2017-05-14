package com.gonyb.share.exception;

import com.gonyb.share.http.DoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gonyb on 2017/5/5.
 */
@ControllerAdvice
//如果返回的为json数据或其它对象，添加该注解  
@ResponseBody
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //添加全局异常处理流程，根据需要设置需要处理的异常
    @ExceptionHandler(value = Exception.class)
    public DoResult defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("服务器异常 请求 url:{}",req.getRequestURL());
        e.printStackTrace();
        return DoResult.FAILED("服务器异常"+e.getMessage());
    }
}
