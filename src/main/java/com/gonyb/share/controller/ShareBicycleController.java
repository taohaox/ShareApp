package com.gonyb.share.controller;

import com.gonyb.share.domain.param.BicycleParam;
import com.gonyb.share.domain.param.BicyclePasswordParam;
import com.gonyb.share.http.DoResult;
import com.gonyb.share.service.ShareBicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 
 * Created by Gonyb on 2017/5/4.
 */
@RestController
@RequestMapping(value = "/shareBicycle")
public class ShareBicycleController {
    @Autowired
    ShareBicycleService shareBicycleService;

    /**
     * 获取车辆密码  
     * @param bicycleParam
     * @return
     */
    @RequestMapping(value = "/find",method = RequestMethod.POST)
    public DoResult saveShareBicycle(@RequestBody BicycleParam bicycleParam){
        return shareBicycleService.saveBicycleInfo(bicycleParam);
    }

    /**
     * 记录密码
     * @param bicycleParam
     * @return
     */
    @RequestMapping(value = "/setPassword",method = RequestMethod.POST)
    public DoResult setPassword(@RequestBody BicyclePasswordParam bicycleParam){
        return shareBicycleService.saveBicyclePassword(bicycleParam.getPassword(),bicycleParam.getId());
    }
    /**
     * 记录密码是否正确
     * @param bicycleParam
     * @return
     */
    @RequestMapping(value = "/passwordIsCorrect",method = RequestMethod.POST)
    public DoResult passwordIsCorrect(@RequestBody BicyclePasswordParam bicycleParam){
        return shareBicycleService.passwordIsCorrect(bicycleParam);
    }

    /**
     * 模糊查询测试
     * @return
     */
    @RequestMapping(value = "/getPassword/{keyword}",method = RequestMethod.GET)
    public DoResult getPassword(@PathVariable String keyword){
        return shareBicycleService.getPassword(keyword);
    }
}
