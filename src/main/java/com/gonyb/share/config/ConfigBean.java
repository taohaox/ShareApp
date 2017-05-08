package com.gonyb.share.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by Gonyb on 2017/5/3.
 */
@Component
@PropertySource(value = "classpath:user.properties")
@ConfigurationProperties(prefix = "gonyb")
public class ConfigBean {
    private String name;
    private int age;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
