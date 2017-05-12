package com.gonyb.share.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * AOP拦截器：记录日志
 * 规则  在controller包下
 *       有 RequestMapping 注解
 * Created by Gonyb on 2017/5/9.
 */
@Aspect
@Component
@Order(5)//定义优先顺序 越小优先级越高
public class ControllerLogsInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ControllerLogsInterceptor.class);
    @Value("${spring.profiles.active}")
    private String env;
    
    /**
     * 定义拦截规则：拦截com.gonyb.share.controller包下面的所有类中，有@RequestMapping注解的方法。 
     */
    @Pointcut("execution(* com.gonyb.share.controller..*(..)) && " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut(){}

    @Before("controllerMethodPointcut()")
    void doBefore(JoinPoint joinPoint) throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        ServletInputStream inputStream = request.getInputStream();
        int length = request.getContentLength();
        byte[] bytes = new byte[length];
        inputStream.read(bytes,0,length);
        logger.info("requestBody:[{}]", new String(bytes));
    }

    @AfterReturning(returning = "ret", pointcut = "controllerMethodPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("response :[{}] " , JSONObject.toJSONString(ret));
    }
}
