package com.smt.soccerbetrestapi.service;

import com.smt.soccerbetrestapi.entity.Match;
import com.smt.soccerbetrestapi.enums.League;
import com.smt.soccerbetrestapi.utils.DoubleUtils;
import com.smt.soccerbetrestapi.utils.SeasonUtils;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SoccerDataService {

    private static final String SOCCER_DATA_HOME = "https://projects.fivethirtyeight.com/soccer-predictions/";

    @Value("${season.start.year.month}")
    private String seasonStart;

    public List<Match> loadAllLeagueMatches() {
        return Arrays.stream(League.values()).flatMap(this::loadMatches).collect(Collectors.toList());
    }

    @SneakyThrows(IOException.class)
    private Stream<Match> loadMatches(League league) {
        Document doc = Jsoup.connect(SOCCER_DATA_HOME + league.getName()).get();
        Elements matchElements = doc.select(".games-container.completed").select(".match-container");
        return matchElements.stream().map(match -> toMatch(match, league));
    }

    private Match toMatch(Element matchElement, League league) {
        String homeTeamName = matchElement.attr("data-team1");
        String awayTeamName = matchElement.attr("data-team2");
        Element matchTopTr = matchElement.select("tr.match-top").first();
        String date = matchTopTr.select("td.date").text();
        int homeScore = Integer.parseInt(matchTopTr.select("span.score").text());
        double homeProb = toProb(matchTopTr.select("td.prob:not(.tie-prob)").text());
        double tieProb = toProb(matchTopTr.select("td.prob.tie-prob").text());
        Element matchBottomTr = matchElement.select("tr.match-bottom").first();
        int awayScore = Integer.parseInt(matchBottomTr.select("span.score").text());
        return new Match(SeasonUtils.toFullDate(date, seasonStart), homeTeamName, awayTeamName,
                homeScore, awayScore, homeProb, tieProb, league.getName());
    }

    private static double toProb(String probStr) {
        return DoubleUtils.round(Double.parseDouble(probStr.substring(0, probStr.length() - 1)) / 100, 2);
    }
}
