package com.smt.betfair.repo;

import com.smt.betfair.entity.TimedOdds;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface TimedOddsRepo extends CrudRepository<TimedOdds, String> {
    List<TimedOdds> findByEventId(long eventId);
}
