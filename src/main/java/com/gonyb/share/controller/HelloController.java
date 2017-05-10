package com.gonyb.share.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Gonyb on 2017/5/3.
 */
@RestController
public class HelloController {
    
    @RequestMapping("/hello")
    public String index(){
        return 456123+"33";
    }
}
