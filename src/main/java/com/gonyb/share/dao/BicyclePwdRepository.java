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
    @Query(value = "SELECT * FROM shared_bicycle_pwd s WHERE s.shared_bicycle_id=?1 ORDER BY s.right_count/s.use_count,s.use_count DESC",nativeQuery = true)
    List<SharedBicyclePwd> findBySharedBicycleId(Integer shared_bicycle_id);

    @Query("select s from SharedBicyclePwd s where s.password=?1 and s.shared_bicycle_id=?2")
    SharedBicyclePwd findByPasswordAndBicycleId(String password,Integer id);


    @Query(value = "select s from SharedBicyclePwd s where s.password like CONCAT('%',?1,'%')")
    List<SharedBicyclePwd> test(String keyword);
}
