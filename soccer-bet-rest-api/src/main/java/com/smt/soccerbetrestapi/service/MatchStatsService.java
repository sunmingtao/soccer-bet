package com.smt.soccerbetrestapi.service;

import com.smt.soccerbetrestapi.entity.Match;
import com.smt.soccerbetrestapi.model.MatchStats;
import com.smt.soccerbetrestapi.model.Team;
import com.smt.soccerbetrestapi.repo.TeamRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchStatsService {

    private final MatchService matchService;

    public List<Team> findTeams(String league, String season) {
        return populateTeamRepo(season).getTeams().stream()
                .filter(team -> StringUtils.equalsIgnoreCase(league, team.getLeague()))
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList());
    }

    public List<MatchStats> findMatchStats(String name, String season) {
        return populateTeamRepo(season).findByName(name).getMatchStatsList();
    }

    private TeamRepo populateTeamRepo(String season) {
        TeamRepo teamRepo = new TeamRepo();
        matchService.findMatches(season).stream().sorted(Comparator.comparing(Match::getMatchDate))
                .forEach(match -> match.addToTeamMatchStats(teamRepo));
        return teamRepo;
    }

}
