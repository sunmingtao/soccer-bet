package com.smt.soccerbetrestapi.service;

import com.smt.soccerbetrestapi.entity.Match;
import com.smt.soccerbetrestapi.model.MatchStats;
import com.smt.soccerbetrestapi.model.Team;
import com.smt.soccerbetrestapi.repo.MatchRepo;
import com.smt.soccerbetrestapi.repo.TeamRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MatchStatsService {

    private final MatchRepo matchRepo;

    public List<Team> findTeams() {
        return populateTeamRepo().getTeams().stream()
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList());
    }

    public List<MatchStats> findMatchStats(String name) {
        return populateTeamRepo().getOrCreate(name).getMatchStatsList();
    }

    private TeamRepo populateTeamRepo() {
        TeamRepo teamRepo = new TeamRepo();
        findAllMatches().sorted(Comparator.comparing(Match::getMatchDate))
                .forEach(match -> match.addToTeamMatchStats(teamRepo));
        return teamRepo;
    }

    private Stream<Match> findAllMatches() {
        return StreamSupport.stream(matchRepo.findAll().spliterator(), false);
    }
}
