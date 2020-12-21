package com.smt.soccerbetrestapi.controller;

import com.smt.soccerbetrestapi.enums.League;
import com.smt.soccerbetrestapi.model.MatchStats;
import com.smt.soccerbetrestapi.model.Team;
import com.smt.soccerbetrestapi.repo.TeamRepo;
import com.smt.soccerbetrestapi.utils.HtmlParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HomeController {

    @PostConstruct
    public void init() {
        HtmlParser.loadLeague(League.LALIGA);
        HtmlParser.loadLeague(League.SERIE_A);
        HtmlParser.loadLeague(League.EPL);
        HtmlParser.loadLeague(League.LIGUE_1);
        HtmlParser.loadLeague(League.BUNDESLIGA);
    }

    @GetMapping("/")
    public String home() {
        return "Welcome";
    }

    @GetMapping("/teams")
    public List<Team> teams() {
        return TeamRepo.teamRepo.getTeams().stream()
                .sorted((team1, team2) -> team1.getName().compareTo(team2.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/team")
    public List<MatchStats> matchStats(@RequestParam String name) {
        return TeamRepo.teamRepo.getOrCreate(name).getMatchStatsList();
    }

}
