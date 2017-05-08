package com.gonyb.share.dao;

import com.gonyb.share.domain.SharedBicycleLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gonyb on 2017/5/5.
 */
@Repository
public interface BicycleLogRepository extends JpaRepository<SharedBicycleLogs,Integer> {
    
}
