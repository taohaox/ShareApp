package com.gonyb.share.controller;

import com.gonyb.share.http.DoResult;
import com.gonyb.share.domain.param.BicycleParam;
import com.gonyb.share.service.ShareBicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * Created by Gonyb on 2017/5/4.
 */
@RestController
@RequestMapping(value = "/shareBicycle")
public class ShareBicycleController {
    @Autowired
    ShareBicycleService shareBicycleService;
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public DoResult saveShareBicycle(@RequestBody BicycleParam bicycleParam){
        return shareBicycleService.saveBicycleInfo(bicycleParam);
    }
}
