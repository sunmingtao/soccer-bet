package com.smt.soccerbetrestapi.controller;

import com.smt.soccerbetrestapi.cron.MatchLoader;
import com.smt.soccerbetrestapi.repo.MatchRepo;
import com.smt.soccerbetrestapi.service.MatchStatsService;
import com.smt.soccerbetrestapi.service.SoccerDataServiceV2;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(HomeController.class)
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SoccerDataServiceV2 soccerDataServiceV2;

    @MockBean
    private MatchRepo matchRepo;

    @MockBean
    private MatchStatsService matchStatsService;

    @MockBean
    private MatchLoader matchLoader;

    @Test
    void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Welcome")));
    }
}
