package com.smt.soccerbetrestapi;

import com.smt.soccerbetrestapi.repo.MatchRepo;
import com.smt.soccerbetrestapi.service.SoccerDataService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SoccerBetRestApiApplicationTests {

	@MockBean
	private SoccerDataService soccerDataService;

	@MockBean
	private MatchRepo matchRepo;

	@Test
	void contextLoads() {
	}

}
