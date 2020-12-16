package com.smt.soccerbetrestapi.repo;

import com.smt.soccerbetrestapi.model.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TeamRepo {

    public static final TeamRepo teamRepo = new TeamRepo();

    private final Map<String, Team> teamMap = new HashMap<>();

    public Team getOrCreate(String teamName) {
        return Optional.ofNullable(teamMap.get(teamName.toLowerCase())).orElseGet(() -> create(teamName));
    }

    private Team create(String teamName) {
        Team team = new Team(teamName.toLowerCase());
        teamMap.put(teamName.toLowerCase(), team);
        return team;
    }
}
