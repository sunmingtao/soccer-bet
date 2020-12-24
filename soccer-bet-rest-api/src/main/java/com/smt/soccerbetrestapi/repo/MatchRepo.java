package com.smt.soccerbetrestapi.repo;

import com.smt.soccerbetrestapi.entity.Match;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface MatchRepo extends CrudRepository<Match, String> {

}
