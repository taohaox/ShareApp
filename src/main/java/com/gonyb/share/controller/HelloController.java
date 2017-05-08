package com.gonyb.share.controller;

import com.gonyb.share.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Gonyb on 2017/5/3.
 */
@RestController
public class HelloController {
    @Autowired
    ConfigBean configBean;
    @RequestMapping("/hello")
    public String index(){
        return configBean.getName()+configBean;
    }
}
