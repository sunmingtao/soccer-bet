package com.smt.soccerbetrestapi.service;

import com.smt.soccerbetrestapi.entity.Match;
import com.smt.soccerbetrestapi.enums.League;
import com.smt.soccerbetrestapi.model.SoccerRawMatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class SoccerDataService {

    private static final String SOCCER_DATA_URL_TEMPLATE = "https://projects.fivethirtyeight.com/soccer-predictions/forecasts/%s_%s_matches.json";

    public List<Match> loadAllLeagueMatches(String season) {
        return Arrays.stream(League.values()).flatMap(league -> loadMatches(season, league)).collect(Collectors.toList());
    }

    public Stream<Match> loadMatches(String season, League league) {
        List<SoccerRawMatch> matches = loadRawMatches(season, league);
        matches.forEach(match -> {
            match.setSeason(season);
            match.setLeagueName(league.getName());
        });
        return matches.stream().filter(SoccerRawMatch::isCompleted).map(SoccerRawMatch::toEntity);
    }

    private List<SoccerRawMatch> loadRawMatches(String season, League league) {
        String url = String.format(SOCCER_DATA_URL_TEMPLATE, season, league.getName());
        log.info("Load soccer data in {} {}, URL = {}", league.getName(), season, url);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<SoccerRawMatch>> matcheResponse = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        List<SoccerRawMatch> rawMatches = matcheResponse.getBody();
        return Optional.ofNullable(rawMatches)
                .orElseThrow(() -> new IllegalArgumentException("Cannot load soccer matches for season " + season + " in league " + league));
    }

}