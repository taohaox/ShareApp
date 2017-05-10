package com.gonyb.share.dao;

import com.gonyb.share.domain.SharedBicyclePwd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Gonyb on 2017/5/5.
 */
@Repository
public interface BicyclePwdRepository extends JpaRepository<SharedBicyclePwd,Integer> {
    @Query("select s from SharedBicyclePwd s where s.shared_bicycle_id=?1 order by s.right_count desc")
    List<SharedBicyclePwd> findBySharedBicycleId(Integer shared_bicycle_id);

    @Query("select s from SharedBicyclePwd s where s.password=?1 and s.shared_bicycle_id=?2")
    SharedBicyclePwd findByPasswordAndBicycleId(String password,Integer id);
}
