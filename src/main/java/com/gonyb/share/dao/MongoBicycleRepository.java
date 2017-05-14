package com.gonyb.share.dao;

import com.gonyb.share.domain.SharedBicycle;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Gonyb on 2017/5/14.
 */
public interface MongoBicycleRepository extends MongoRepository<SharedBicycle, Integer> {
    SharedBicycle findByShareCode(String shareCode);
}
