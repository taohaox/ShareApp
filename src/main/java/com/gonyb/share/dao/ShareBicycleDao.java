package com.gonyb.share.dao;

import com.gonyb.share.domain.SharedBicycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by Gonyb on 2017/5/4.
 */
@Repository
public class ShareBicycleDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public int save(SharedBicycle sharedBicycle) {
        return jdbcTemplate.update("insert into shared_bicycle(share_code, create_time,update_time) values(?, ?,?)",
                sharedBicycle.getShareCode(),new Date(),new Date());

    }
}
