package com.gonyb.share.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * 请求日志拦截器
 * Created by Gonyb on 2017/5/9.
 */
public class RequestLogInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLogInterceptor.class);
    /**
     * 前置检查
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ip = request.getRemoteAddr();
        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime);
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 获取用户token
        Method method = handlerMethod.getMethod();
        logger.info("用户:"+ip+",访问目标:"+request.getRequestURL());
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuffer headerInfo = new StringBuffer();
        headerInfo.append("header[");
        int i = 0;
        while (headerNames.hasMoreElements()){
            i++;
            String s = headerNames.nextElement();
            headerInfo.append(s+":"+request.getHeader(s)+"\t");
            if(i%4==0){
                headerInfo.append("\n");
            }
        }
        headerInfo.append("]");
        logger.debug(headerInfo.toString());
//        logger.info(request.getContextPath());
//        //clone HttpServletRequest
//        request = new RepeatedlyReadRequestWrapper(request);
//        int len = request.getContentLength();
//        ServletInputStream iii = request.getInputStream();
//        byte[] buffer = new byte[len];
//        iii.read(buffer, 0, len);
//        logger.info("requestBody:{}",new String(buffer));
        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }
    // controller处理完成
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        long startTime = (Long) request.getAttribute("requestStartTime");

        long endTime = System.currentTimeMillis();

        long executeTime = endTime - startTime;

        // log it
        if (executeTime > 1000) {
            logger.info("[" + method.getDeclaringClass().getName() + "." + method.getName() + "] 执行耗时 : "
                    + executeTime + "ms");
        } else {
            logger.info("[" + method.getDeclaringClass().getSimpleName() + "." + method.getName() + "] 执行耗时 : "
                    + executeTime + "ms");
        }

    }
}
