package com.gonyb.share.dao;

import com.gonyb.share.domain.SharedBicycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Gonyb on 2017/5/5.
 */
@Repository
public interface BicycleRepository extends JpaRepository<SharedBicycle,Integer> {
    @Query("select s from SharedBicycle s where s.shareCode=?1")
    SharedBicycle findByShareCode(String shareCode);
}
