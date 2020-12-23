package com.smt.soccerbetrestapi.service;

import com.smt.soccerbetrestapi.enums.League;
import com.smt.soccerbetrestapi.model.Match;
import com.smt.soccerbetrestapi.model.Team;
import com.smt.soccerbetrestapi.repo.TeamRepo;
import com.smt.soccerbetrestapi.utils.DoubleUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoccerDataService {

    private static final String SOCCER_DATA_HOME = "https://projects.fivethirtyeight.com/soccer-predictions/";

    @SneakyThrows(IOException.class)
    public List<Match> loadMatches(League league) {
        Document doc = Jsoup.connect(SOCCER_DATA_HOME + league.getName()).get();
        Elements matchElements = doc.select(".games-container.completed").select(".match-container");
        return matchElements.stream().map(SoccerDataService::toMatch).collect(Collectors.toList());
    }

    public void loadLeague(League league) {
        List<Match> matches = loadMatches(league);
        Collections.reverse(matches);
        matches.forEach(Match::addToTeamMatchStats);
    }

    private static Match toMatch(Element matchElement) {
        String homeTeamName = matchElement.attr("data-team1");
        String awayTeamName = matchElement.attr("data-team2");
        Element matchTopTr = matchElement.select("tr.match-top").first();
        String date = matchTopTr.select("td.date").text();
        int homeScore = Integer.parseInt(matchTopTr.select("span.score").text());
        double homeProb = toProb(matchTopTr.select("td.prob:not(.tie-prob)").text());
        double tieProb = toProb(matchTopTr.select("td.prob.tie-prob").text());
        Element matchBottomTr = matchElement.select("tr.match-bottom").first();
        int awayScore = Integer.parseInt(matchBottomTr.select("span.score").text());
        Team homeTeam = TeamRepo.teamRepo.getOrCreate(homeTeamName);
        Team awayTeam = TeamRepo.teamRepo.getOrCreate(awayTeamName);
        return new Match(date, homeTeam, awayTeam, homeScore, awayScore, homeProb, tieProb);
    }

    private static double toProb(String probStr) {
        return DoubleUtils.round(Double.parseDouble(probStr.substring(0, probStr.length() - 1)) / 100, 2);
    }
}
