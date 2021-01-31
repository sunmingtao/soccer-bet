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

        double profitOnUnderdogOddsBetween2And3 = games.stream().filter(game -> game.isUnderdogOddsBetween(3.00, 3.00))
                .map(Game::getProfitOnUnderdog).reduce(0d, Double::sum);
        System.out.println("Total profit on underdog with odds between 2 and 3 is " + profitOnUnderdogOddsBetween2And3);
        double profitOnFavouriteOddsBetween2And3 = games.stream().filter(game -> game.isUnderdogOddsBetween(2.00, 3.00))
                .map(Game::getProfitOnFavourite).reduce(0d, Double::sum);
        System.out.println("Total profit on favourite with odds between 2 and 3 is " + profitOnFavouriteOddsBetween2And3);

        double profitOnUnderdogOddsBetween3And4 = games.stream().filter(game -> game.isUnderdogOddsBetween(3.00, 4.00))
                .map(Game::getProfitOnUnderdog).reduce(0d, Double::sum);
        System.out.println("Total profit on underdog with odds between 3 and 4 is " + profitOnUnderdogOddsBetween3And4);
        double profitOnFavouriteOddsBetween3And4 = games.stream().filter(game -> game.isUnderdogOddsBetween(3.00, 4.00))
                .map(Game::getProfitOnFavourite).reduce(0d, Double::sum);
        System.out.println("Total profit on favourite with odds between 3 and 4 is " + profitOnFavouriteOddsBetween3And4);

        double profitOnUnderdogOddsBetween4And5 = games.stream().filter(game -> game.isUnderdogOddsBetween(4.00, 5.00))
                .map(Game::getProfitOnUnderdog).reduce(0d, Double::sum);
        System.out.println("Total profit on underdog with odds between 4 and 5 is " + profitOnUnderdogOddsBetween4And5);
        double profitOnFavouriteOddsBetween4And5 = games.stream().filter(game -> game.isUnderdogOddsBetween(4.00, 5.00))
                .map(Game::getProfitOnFavourite).reduce(0d, Double::sum);
        System.out.println("Total profit on favourite with odds between 4 and 5 is " + profitOnFavouriteOddsBetween4And5);

        double profitOnUnderdogOddsBetween5And100 = games.stream().filter(game -> game.isUnderdogOddsBetween(5.00, 100.00))
                .map(Game::getProfitOnUnderdog).reduce(0d, Double::sum);
        System.out.println("Total profit on underdog with odds between 5 and 100 is " + profitOnUnderdogOddsBetween5And100);
        double profitOnFavouriteOddsBetween5And100 = games.stream().filter(game -> game.isUnderdogOddsBetween(5.00, 100.00))
                .map(Game::getProfitOnFavourite).reduce(0d, Double::sum);
        System.out.println("Total profit on favourite with odds between 5 and 100 is " + profitOnFavouriteOddsBetween5And100);

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
