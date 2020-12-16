package com.smt.soccerbetrestapi.utils;

import com.smt.soccerbetrestapi.model.Match;
import com.smt.soccerbetrestapi.model.Team;
import com.smt.soccerbetrestapi.repo.TeamRepo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class HtmlParser {

    @SneakyThrows(IOException.class)
    public static void main(String[] args) {
        //Document doc = Jsoup.connect("https://projects.fivethirtyeight.com/soccer-predictions/premier-league/").get();
//        Document doc = Jsoup.connect("https://projects.fivethirtyeight.com/soccer-predictions/la-liga/").get();
        Document doc = Jsoup.connect("https://projects.fivethirtyeight.com/soccer-predictions/serie-a/").get();
        Elements matchElements = doc.select(".games-container.completed").select(".match-container");
        List<Match> matches = matchElements.stream().map(element -> toMatch(element)).collect(Collectors.toList());
        Collections.reverse(matches);
        matches.forEach(Match::addToTeamMatchStats);
        Team manCity = TeamRepo.teamRepo.getOrCreate("Atalanta");
        manCity.getMatchStatsList().forEach(System.out::println);
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
        return Double.parseDouble(probStr.substring(0, probStr.length() - 1)) / 100;
    }
}
