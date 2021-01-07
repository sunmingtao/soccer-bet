package com.smt.soccerbetrestapi.service;

import com.smt.soccerbetrestapi.entity.Match;
import com.smt.soccerbetrestapi.enums.League;
import com.smt.soccerbetrestapi.model.SoccerRawMatch;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

@Service
public class SoccerDataServiceV2 {

    private static final String SOCCER_DATA_URL_TEMPLATE = "https://projects.fivethirtyeight.com/soccer-predictions/forecasts/%s_%s_matches.json";

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
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<SoccerRawMatch>> matcheResponse = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        return matcheResponse.getBody();
    }

}
