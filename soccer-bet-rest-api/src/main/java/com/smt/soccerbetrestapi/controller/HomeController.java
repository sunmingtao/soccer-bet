package com.smt.soccerbetrestapi.controller;

import com.smt.soccerbetrestapi.enums.League;
import com.smt.soccerbetrestapi.model.Match;
import com.smt.soccerbetrestapi.model.MatchStats;
import com.smt.soccerbetrestapi.model.SimpleMatch;
import com.smt.soccerbetrestapi.model.Team;
import com.smt.soccerbetrestapi.repo.MatchRepo;
import com.smt.soccerbetrestapi.repo.SimpleMatchRepo;
import com.smt.soccerbetrestapi.repo.TeamRepo;
import com.smt.soccerbetrestapi.service.SoccerDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final SoccerDataService soccerDataService;
    private final MatchRepo matchRepo;
    private final SimpleMatchRepo simpleMatchRepo;

    @PostConstruct
    public void init() {
        soccerDataService.loadLeague(League.LALIGA);
        soccerDataService.loadLeague(League.SERIE_A);
        soccerDataService.loadLeague(League.EPL);
        soccerDataService.loadLeague(League.LIGUE_1);
        soccerDataService.loadLeague(League.BUNDESLIGA);
    }

    @GetMapping("/")
    public String home() {
        SimpleMatch match = new SimpleMatch();
        match.setMatchDate("2020");
        simpleMatchRepo.save(match);
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
