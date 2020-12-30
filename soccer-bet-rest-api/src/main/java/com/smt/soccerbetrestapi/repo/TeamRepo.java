package com.smt.soccerbetrestapi.repo;

import com.smt.soccerbetrestapi.enums.League;
import com.smt.soccerbetrestapi.model.Team;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TeamRepo {

    private final Map<String, Team> teamMap = new HashMap<>();

    public Set<Team> getTeams() {
        return teamMap.values().stream().collect(Collectors.toSet());
    }

    public Team findByName(String teamName) {
        return Optional.ofNullable(teamMap.get(teamName.toLowerCase())).orElse(null);
    }

    public List<Team> findByLeague(League league) {
        return teamMap.values().stream()
                .filter(team -> StringUtils.equalsIgnoreCase(team.getLeague(), league.getName()))
                .collect(Collectors.toList());
    }

    public Team findOrCreate(String teamName, String league) {
        return Optional.ofNullable(teamMap.get(teamName.toLowerCase())).orElseGet(() -> create(teamName, league));
    }

    private Team create(String teamName, String league) {
        Team team = new Team(teamName.toLowerCase(), league);
        teamMap.put(teamName.toLowerCase(), team);
        return team;
    }

}
