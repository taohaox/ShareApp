package com.gonyb.share.service;

import com.gonyb.share.http.DoResult;
import com.gonyb.share.dao.BicycleLogRepository;
import com.gonyb.share.dao.BicyclePwdRepository;
import com.gonyb.share.dao.BicycleRepository;
import com.gonyb.share.dao.ShareBicycleDao;
import com.gonyb.share.domain.SharedBicycle;
import com.gonyb.share.domain.SharedBicycleLogs;
import com.gonyb.share.domain.SharedBicyclePwd;
import com.gonyb.share.domain.param.BicycleParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Gonyb on 2017/5/4.
 */
@Service
public class ShareBicycleService {
    @Autowired
    ShareBicycleDao shareBicycleDao;
    @Autowired
    BicycleRepository bicycleRepository;
    @Autowired
    BicycleLogRepository bicycleLogRepository;
    @Autowired
    BicyclePwdRepository bicyclePwdRepository;
    @Transactional
    public DoResult saveBicycleInfo(BicycleParam bicycleParam){
        SharedBicycle byCode = bicycleRepository.findByCode(bicycleParam.getShare_code());
        if (byCode == null) { //如果数据库没有此单车数据  则保存
            byCode = new SharedBicycle();
            byCode.setCreate_time(new Date());
            byCode.setShare_code(bicycleParam.getShare_code());
            byCode.setUpdate_time(new Date());
            byCode = bicycleRepository.save(byCode);
        }
        saveBicyclerLog(bicycleParam, byCode.getId());
        return DoResult.SUCCESS(byCode);
    }

    public DoResult saveBicyclePassword(String password,Integer sharedBicycleId){
        SharedBicyclePwd byPassword = bicyclePwdRepository.findByPassword(password);
        if (password != null) {  //密码记录增加
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

        SharedBicycle one = bicycleRepository.findOne(sharedBicycleId);

        List<SharedBicyclePwd> bySharedBicycleId = bicyclePwdRepository.findBySharedBicycleId(sharedBicycleId); //取出
        String password1 = bySharedBicycleId.get(0).getPassword();
        if(!password1.equals(one.getPassword())){//取出正确率最高的密码保存  如果和保存的不一致 则修改
            one.setPassword(password);
            bicycleRepository.save(one);
        }
        return DoResult.SUCCESS(bySharedBicycleId.get(0));
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

}
