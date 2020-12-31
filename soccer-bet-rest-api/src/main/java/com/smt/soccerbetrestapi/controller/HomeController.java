package com.smt.soccerbetrestapi.controller;

import com.smt.soccerbetrestapi.cron.MatchLoader;
import com.smt.soccerbetrestapi.enums.League;
import com.smt.soccerbetrestapi.model.MatchStats;
import com.smt.soccerbetrestapi.model.Team;
import com.smt.soccerbetrestapi.repo.MatchRepo;
import com.smt.soccerbetrestapi.service.MatchStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        printAllProfit();
    }

    public void printAllProfit() {
        System.out.println("Number of matches: "
                + matchStatsService.findTeams().stream().flatMap(team -> team.getMatchStatsList().stream()).count() / 2);
        System.out.println("Total profit: " + matchStatsService.findTeams().stream().map(Team::getTotalProfit).reduce(0d, Double::sum));
        System.out.println("Total profit as fav: " + matchStatsService.findTeams().stream().map(Team::getTotalProfitAsFav).reduce(0d, Double::sum));
        System.out.println("Total profit back draw: " + matchStatsService.findTeams().stream().flatMap(team -> team.getMatchStatsList().stream())
                .map(MatchStats::getProfitBackOnDraw).reduce(0d, Double::sum) / 2);
        System.out.println("Total profit lay draw (Fixed win): " + matchStatsService.findTeams().stream().flatMap(team -> team.getMatchStatsList().stream())
                .map(MatchStats::getProfitLayOnDraw).reduce(0d, Double::sum) / 2);
        System.out.println("Total profit lay draw2 (Fixed liability): " + matchStatsService.findTeams().stream().flatMap(team -> team.getMatchStatsList().stream())
                .map(MatchStats::getProfitLayOnDraw2).reduce(0d, Double::sum) / 2);
    }

    @GetMapping("/")
    public String home() {
        return "Welcome";
    }

    @GetMapping("/leagues")
    public List<League> leagues() {
        return List.of(League.values());
    }

    @GetMapping("/teams")
    public List<Team> teams() {
        return matchStatsService.findTeams();
    }

    @GetMapping("/teams/{league}")
    public List<Team> teamsByLeague(@PathVariable String league) {
        return matchStatsService.findTeams(league);
    }

    @GetMapping("/team")
    public List<MatchStats> matchStats(@RequestParam String name) {
        return matchStatsService.findMatchStats(name);
    }

}
