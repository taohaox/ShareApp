package com.gonyb.share.service;

import com.gonyb.share.dao.*;
import com.gonyb.share.domain.SharedBicycle;
import com.gonyb.share.domain.SharedBicycleLogs;
import com.gonyb.share.domain.SharedBicyclePwd;
import com.gonyb.share.domain.param.BicycleParam;
import com.gonyb.share.domain.param.BicyclePasswordParam;
import com.gonyb.share.http.DoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Gonyb on 2017/5/4.
 */
@Service
public class ShareBicycleService {
    private static final Logger logger = LoggerFactory.getLogger(ShareBicycleService.class);
    @Resource
    ShareBicycleDao shareBicycleDao;
    @Resource
    private BicycleRepository bicycleRepository;
    @Resource
    private BicycleLogRepository bicycleLogRepository;
    @Resource
    private BicyclePwdRepository bicyclePwdRepository;
    @Resource
    private MongoBicycleRepository mongoRepository;

    @Transactional
    public DoResult saveBicycleInfo(BicycleParam bicycleParam){
        SharedBicycle byShareCode = bicycleRepository.findByShareCode(bicycleParam.getShare_code());
        if (byShareCode == null) { //如果数据库没有此单车数据  则保存
            byShareCode = new SharedBicycle();
            byShareCode.setCreateTime(new Date());
            byShareCode.setShareCode(bicycleParam.getShare_code());
            byShareCode.setUpdateTime(new Date());
            byShareCode = bicycleRepository.save(byShareCode);
        }
//        SharedBicycle byShareCode = mongoRepository.findByShareCode(bicycleParam.getShare_code());
//        if (byShareCode == null) { //如果数据库没有此单车数据  则保存
//            byShareCode = new SharedBicycle();
//            byShareCode.setCreateTime(new Date());
//            byShareCode.setShareCode(bicycleParam.getShare_code());
//            byShareCode.setUpdateTime(new Date());
//            byShareCode = mongoRepository.save(byShareCode);
//        }
        saveBicyclerLog(bicycleParam, byShareCode.getId());
        return DoResult.SUCCESS(byShareCode);
    }

    public DoResult saveBicyclePassword(String password,Integer sharedBicycleId){
        SharedBicyclePwd byPassword = bicyclePwdRepository.findByPasswordAndBicycleId(password,sharedBicycleId);
        if (byPassword != null) {  //密码记录增加
            byPassword.setRight_count(byPassword.getRight_count() + 1);
            byPassword.setUse_count(byPassword.getUse_count() + 1);
        } else {
            byPassword = new SharedBicyclePwd();
            byPassword.setUse_count(1);
            byPassword.setCreate_time(new Date());
            byPassword.setPassword(password);
            byPassword.setRight_count(1);
            byPassword.setShared_bicycle_id(sharedBicycleId);
        }
        byPassword.setUpdate_time(new Date());
        bicyclePwdRepository.save(byPassword); //保存密码记录 
        return changePwd(sharedBicycleId);
    }

    /**
     * 变更密码
     * @param sharedBicycleId
     * @return
     */
    private DoResult changePwd(Integer sharedBicycleId) {
        SharedBicycle one = bicycleRepository.findOne(sharedBicycleId);
        //按正确数排序
        List<SharedBicyclePwd> bySharedBicycleId = bicyclePwdRepository.findBySharedBicycleId(sharedBicycleId); //取出
        String password1 = bySharedBicycleId.get(0).getPassword();
        if(!password1.equals(one.getPassword())){//取出正确率最高的密码保存  如果和保存的不一致 则修改
            one.setPassword(password1);
            bicycleRepository.save(one);
        }
        return DoResult.SUCCESS(bySharedBicycleId.get(0));
    }

    /**
     * 记录密码是否正确
     * @param bicycleParam
     * @return
     */
    public DoResult passwordIsCorrect(BicyclePasswordParam bicycleParam) {
        SharedBicyclePwd byPassword = bicyclePwdRepository.findByPasswordAndBicycleId
                (bicycleParam.getPassword(),bicycleParam.getId());
        if (byPassword == null) {
            return DoResult.FAILED("无此密码记录");
        } 
        //正确数+1
        if (bicycleParam.isCorrect()) {
            byPassword.setRight_count(byPassword.getRight_count() + 1);
            byPassword.setUse_count(byPassword.getUse_count() + 1);
        } else {
            byPassword.setRight_count(byPassword.getRight_count() -1);
            byPassword.setUse_count(byPassword.getUse_count() + 1);
        } 
        byPassword.setUpdate_time(new Date());
        bicyclePwdRepository.save(byPassword); //保存密码记录 
        changePwd(bicycleParam.getId());
        return DoResult.SUCCESS("已记录",null);
    }
    
    /**
     * 保存访问日志
     * @param bicycleParam
     * @param shareBicycleId
     */
    private void saveBicyclerLog(BicycleParam bicycleParam, Integer shareBicycleId) {
        SharedBicycleLogs sharedBicycleLogs = new SharedBicycleLogs();
        sharedBicycleLogs.setShared_bicycle_id(shareBicycleId);
        sharedBicycleLogs.setArea(bicycleParam.getArea());
        sharedBicycleLogs.setCity(bicycleParam.getCity());
        sharedBicycleLogs.setProvince(bicycleParam.getProvince());
        sharedBicycleLogs.setCreate_time(new Date());
        sharedBicycleLogs.setLatitude(bicycleParam.getLatitude());
        sharedBicycleLogs.setLongitude(bicycleParam.getLongitude());
        bicycleLogRepository.save(sharedBicycleLogs);
    }


    public DoResult getPassword(String keyword) {
        logger.info(keyword);
        List<SharedBicyclePwd> test = bicyclePwdRepository.test(keyword);
        return DoResult.SUCCESS(test);
    }
}
