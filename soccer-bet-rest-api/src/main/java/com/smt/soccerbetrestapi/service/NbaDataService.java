package com.smt.soccerbetrestapi.service;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NbaDataService {
    private static final String NBA_DATA_HOME_TEMPLATE = "https://projects.fivethirtyeight.com/%s-nba-predictions/games/";

    @Value("${nba.season}")
    private String season;

    @SneakyThrows(IOException.class)
    public static void main(String[] args) {
        String nbaDataUrl = String.format(NBA_DATA_HOME_TEMPLATE, "2021");
        Document doc = Jsoup.connect(nbaDataUrl).get();
        Elements dayElements = doc.select("div#completed-days").select("table[data-game]");
        //System.out.println(dayElements.size());
        dayElements.stream().forEach(element -> System.out.println(element.attr("data-game")));
    }
}
