package com.smt.soccerbetrestapi.controller;

import com.smt.soccerbetrestapi.enums.League;
import com.smt.soccerbetrestapi.model.MatchStats;
import com.smt.soccerbetrestapi.model.Team;
import com.smt.soccerbetrestapi.repo.MatchRepo;
import com.smt.soccerbetrestapi.repo.TeamRepo;
import com.smt.soccerbetrestapi.service.SoccerDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final SoccerDataService soccerDataService;
    private final MatchRepo matchRepo;

    @PostConstruct
    public void init() {
        soccerDataService.loadLeague(League.LALIGA);
        soccerDataService.loadLeague(League.SERIE_A);
        soccerDataService.loadLeague(League.EPL);
        soccerDataService.loadLeague(League.LIGUE_1);
        soccerDataService.loadLeague(League.BUNDESLIGA);
        matchRepo.saveAll(soccerDataService.loadMatches(League.LIGUE_1));
    }

    @GetMapping("/")
    public String home() {
        return "Welcome";
    }

    @GetMapping("/teams")
    public List<Team> teams() {
        return TeamRepo.teamRepo.getTeams().stream()
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList());
    }

    @GetMapping("/team")
    public List<MatchStats> matchStats(@RequestParam String name) {
        return TeamRepo.teamRepo.getOrCreate(name).getMatchStatsList();
    }

}
