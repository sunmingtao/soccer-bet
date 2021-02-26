package com.smt.betfair.repo;

import com.smt.betfair.entity.MatchUnderWatch;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;


@EnableScan
public interface MatchUnderWatchRepo extends CrudRepository<MatchUnderWatch, String> {

}
