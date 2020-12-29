package com.smt.soccerbetrestapi.controller;

import com.smt.soccerbetrestapi.cron.MatchLoader;
import com.smt.soccerbetrestapi.enums.League;
import com.smt.soccerbetrestapi.model.MatchStats;
import com.smt.soccerbetrestapi.model.Team;
import com.smt.soccerbetrestapi.repo.MatchRepo;
import com.smt.soccerbetrestapi.service.MatchStatsService;
import com.smt.soccerbetrestapi.service.SoccerDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final MatchLoader matchLoader;
    private final MatchStatsService matchStatsService;

    @PostConstruct
    public void init() {
        matchLoader.load();
    }

    @GetMapping("/")
    public String home() {
        return "Welcome";
    }

    @GetMapping("/teams")
    public List<Team> teams() {
        return matchStatsService.findTeams();
    }

    @GetMapping("/team")
    public List<MatchStats> matchStats(@RequestParam String name) {
        return matchStatsService.findMatchStats(name);
    }

}
