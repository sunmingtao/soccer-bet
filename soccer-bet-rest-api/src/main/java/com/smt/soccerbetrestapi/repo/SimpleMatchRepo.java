package com.smt.soccerbetrestapi.repo;

import com.smt.soccerbetrestapi.model.SimpleMatch;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface SimpleMatchRepo extends CrudRepository<SimpleMatch, String> {

}
