package com.gonyb.share.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于ControllerLogsInterceptor  目测只是体验功能 不会使用
 * Created by Gonyb on 2017/5/9.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ControllerAnnotation {
    
}
