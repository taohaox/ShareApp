package com.gonyb.share.interceptor;

import com.gonyb.share.http.DoResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 拦截器：目测只是体验功能 不会使用
 * 规则  在controller包下
 *       有 RequestMapping 注解 有 ControllerAnnotation 注解
 * Created by Gonyb on 2017/5/9.
 */
@Aspect
@Component
public class ControllerLogsInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ControllerLogsInterceptor.class);
    @Value("${spring.profiles.active}")
    private String env;
    
    /**
     * 定义拦截规则：拦截com.gonyb.share.controller包下面的所有类中，有@RequestMapping注解的方法。 
     */
    @Pointcut("execution(* com.gonyb.share.controller..*(..)) && " +
            "@annotation(org.springframework.web.bind.annotation.RequestMapping)&&" +
            "@annotation(com.gonyb.share.annotation.ControllerAnnotation)")
    public void controllerMethodPointcut(){}
    @Around("controllerMethodPointcut()")//环绕通知
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = pjp.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());//获取被拦截的方法  
        String methodName = currentMethod.getName(); //获取被拦截的方法名  
        logger.info("请求开始，方法：{}", methodName);
        Object result = null;
        Object[] args = pjp.getArgs();
        for(Object arg : args){
            if (arg instanceof Map<?, ?>) {
                //提取方法中的MAP参数，用于记录进日志中  
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) arg;
                map.forEach((k, v)->logger.info("{1}:{2}",k,v));
            }else if(arg instanceof HttpServletRequest){
                HttpServletRequest request = (HttpServletRequest) arg;
                //获取query string 或 posted form data参数  
                Map<String, String[]> paramMap = request.getParameterMap();
                if(paramMap!=null && paramMap.size()>0){
                    paramMap.forEach((k, v)->logger.info("请求参数 {1}:{2}",k,v));
                }
            }else if(arg instanceof HttpServletResponse){
                //do nothing...  
            }else{
                //allParams.add(arg);  
                logger.info("arg: {}", arg);
            }
        }

        // 一切正常的情况下，继续执行被拦截的方法  
        result = pjp.proceed();
        if(result instanceof DoResult){
            long costMs = System.currentTimeMillis() - beginTime;
            logger.info("{}请求结束，耗时：{}ms", methodName, costMs);
        }
        logger.info(result.toString());
        return result;
    }
}
