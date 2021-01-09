package com.smt.soccerbetrestapi.controller;

import com.smt.soccerbetrestapi.cron.MatchLoader;
import com.smt.soccerbetrestapi.enums.League;
import com.smt.soccerbetrestapi.model.MatchStats;
import com.smt.soccerbetrestapi.model.TeamList;
import com.smt.soccerbetrestapi.repo.MatchRepo;
import com.smt.soccerbetrestapi.service.MatchStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
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

    @GetMapping("/leagues")
    public List<League> leagues() {
        return List.of(League.values());
    }

    @GetMapping("/teams/{league}")
    public TeamList teamsByLeague(@PathVariable String league) {
        log.info("load teams by league {}", league);
        return new TeamList(matchStatsService.findTeams(league));
    }

    @GetMapping("/team")
    public List<MatchStats> matchStats(@RequestParam String name) {
        return matchStatsService.findMatchStats(name);
    }

}
