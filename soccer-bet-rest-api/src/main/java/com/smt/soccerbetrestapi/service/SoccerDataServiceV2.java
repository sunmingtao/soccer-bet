package com.smt.soccerbetrestapi.service;

import com.smt.soccerbetrestapi.entity.Match;
import com.smt.soccerbetrestapi.enums.League;
import com.smt.soccerbetrestapi.model.RawMatch;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SoccerDataServiceV2 {

    private static final String SOCCER_DATA_URL_TEMPLATE = "https://projects.fivethirtyeight.com/soccer-predictions/forecasts/%s_%s_matches.json";

    public static void main(String[] args) {
        String url = String.format(SOCCER_DATA_URL_TEMPLATE, 2020, League.EPL.getName());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<RawMatch>> actualExample = restTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<>() {});
        List<RawMatch> exampleList = actualExample.getBody();
        exampleList.forEach(System.out::println);
    }
}
