package com.gonyb.share.filter;


import com.gonyb.share.interceptor.ControllerLogsInterceptor;
import com.gonyb.share.interceptor.RepeatedlyReadRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 日志过滤器
 */
public class RequestLogFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(ControllerLogsInterceptor.class);


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		if (servletRequest instanceof HttpServletRequest) {
			servletRequest = new RepeatedlyReadRequestWrapper((HttpServletRequest) servletRequest);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}
}
