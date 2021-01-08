package com.smt.soccerbetrestapi.service;

import com.smt.soccerbetrestapi.entity.NbaGame;
import com.smt.soccerbetrestapi.model.nba.NbaRawData;
import com.smt.soccerbetrestapi.model.nba.NbaRawGame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
public class NbaDataService {
    private static final String NBA_DATA_HOME_TEMPLATE = "https://projects.fivethirtyeight.com/%s-nba-predictions/data.json";

    public Stream<NbaGame> loadGames(String season) {
        List<NbaRawGame> matches = loadRawGames(season).getGames();
        matches.forEach(match -> match.setSeason(season));
        return matches.stream().filter(NbaRawGame::isCompleted).map(NbaRawGame::toEntity);
    }

    private NbaRawData loadRawGames(String season) {
        String url = String.format(NBA_DATA_HOME_TEMPLATE, season);
        log.info("Load NBA data for season {}, URL = {}", season, url);
        RestTemplate restTemplate = new RestTemplate();
        NbaRawData nbaRawData = restTemplate.getForObject(url, NbaRawData.class);
        return Optional.ofNullable(nbaRawData)
                .orElseThrow(() -> new IllegalArgumentException("Cannot lead nba data for season " + season));
    }

}
