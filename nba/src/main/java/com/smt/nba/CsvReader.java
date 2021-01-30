package com.smt.nba;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.smt.nba.csvbean.GameCsv;
import com.smt.nba.csvbean.MoneyLineCsv;
import com.smt.nba.model.Game;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader {

    public static void main(String[] args) throws Exception {
        Path moneyLinePath = Paths.get(
                ClassLoader.getSystemResource("nba_betting_money_line.csv").toURI());
        List<MoneyLineCsv> moneyLineCsvList = beanBuilderExample(moneyLinePath, MoneyLineCsv.class);

        System.out.println("Read money line completed");
        Path gamePath = Paths.get(
                ClassLoader.getSystemResource("nba_games_all.csv").toURI());
        List<GameCsv> gameCsvList = beanBuilderExample(gamePath, GameCsv.class);
        System.out.println("Read all games completed");

        List<Game> games = Game.toGames(gameCsvList, moneyLineCsvList, "238");
        System.out.println("Total number of games " + games.size());

        double profitOnUnderdog = games.stream().map(Game::getProfitOnUnderdog).reduce(0d, Double::sum);
        System.out.println("Total profit on underdog is " + profitOnUnderdog);
        double profitOnFavourite = games.stream().map(Game::getProfitOnFavourite).reduce(0d, Double::sum);
        System.out.println("Total profit on favourite is " + profitOnFavourite);
    }

    @SneakyThrows(IOException.class)
    public static <T> List<T> beanBuilderExample(Path path, Class<T> clazz) {
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(clazz);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(br)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        }
    }

}
