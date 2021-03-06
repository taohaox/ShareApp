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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

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
        logger.info("ARGS : " +  JSONObject.toJSONString(joinPoint.getArgs()));
       //获取所有参数方法一：header
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("head[");
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String s = headerNames.nextElement();
            stringBuilder.append(s+": "+request.getHeader(s) +",");
        }
        stringBuilder.append("]\n queryParams[");

        Enumeration<String> enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName = enu.nextElement();
            stringBuilder.append(paraName+": "+request.getParameter(paraName));
        }
        stringBuilder.append("]");
        logger.info(stringBuilder.toString());

    }

    @AfterReturning(returning = "ret", pointcut = "controllerMethodPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("response :[{}] " , JSONObject.toJSONString(ret));
    }
}
