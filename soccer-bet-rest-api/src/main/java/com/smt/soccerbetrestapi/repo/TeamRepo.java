package com.smt.soccerbetrestapi.repo;

import com.smt.soccerbetrestapi.model.Team;

import java.util.*;
import java.util.stream.Collectors;

public class TeamRepo {

    private final Map<String, Team> teamMap = new HashMap<>();

    public Set<Team> getTeams() {
        return teamMap.values().stream().collect(Collectors.toSet());
    }

    public Team getOrCreate(String teamName) {
        return Optional.ofNullable(teamMap.get(teamName.toLowerCase())).orElseGet(() -> create(teamName));
    }

    private Team create(String teamName) {
        Team team = new Team(teamName.toLowerCase());
        teamMap.put(teamName.toLowerCase(), team);
        return team;
    }
}
