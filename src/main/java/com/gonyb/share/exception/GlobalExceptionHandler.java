package com.gonyb.share.exception;

import com.gonyb.share.http.DoResult;
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
    //添加全局异常处理流程，根据需要设置需要处理的异常，本文以MethodArgumentNotValidException为例  
    @ExceptionHandler(value=Exception.class)
    public DoResult MethodArgumentNotValidHandler(HttpServletRequest request,
                                                Exception exception) throws Exception{
        //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息  
        return DoResult.FAILED(exception.getMessage());
    }
}
