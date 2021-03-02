package com.smt.betfair.repo;

import com.smt.betfair.entity.HistoryEvent;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;


@EnableScan
public interface HistoryEventRepo extends CrudRepository<HistoryEvent, String> {

}
