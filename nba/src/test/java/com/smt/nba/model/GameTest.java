package com.smt.nba.model;

import com.opencsv.bean.CsvBindByName;
import com.smt.nba.csvbean.GameCsv;
import com.smt.nba.csvbean.MoneyLineCsv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import org.junit.jupiter.api.Nested;

import java.util.Arrays;
import java.util.List;

@RunWith(JUnitPlatform.class)
class GameTest {

    @Test
    void usOddsToDecimalOdds() {
        Assertions.assertEquals(2d, Game.usOddsToDecimalOdds(100));
        Assertions.assertEquals(2d, Game.usOddsToDecimalOdds(-100));
        Assertions.assertEquals(4d, Game.usOddsToDecimalOdds(300));
        Assertions.assertEquals(4d/3, Game.usOddsToDecimalOdds(-300));
    }

    @Nested
    class Test_toGames {

        @Test
        void test() {
            GameCsv gameCsv1 = new GameCsv("001", "2020-01-01", "", "1", "2", "t", "W", "2020");
            GameCsv gameCsv2 = new GameCsv("002", "2020-01-02", "", "3", "4", "f", "L", "2020");
            GameCsv gameCsv3 = new GameCsv("003", "2020-01-03", "", "5", "6", "t", "W", "2020");
            MoneyLineCsv moneyLineCsv1 = new MoneyLineCsv("001", "Pinnacle", "100", "1", "2", 110.00, -110.00);
            MoneyLineCsv moneyLineCsv2 = new MoneyLineCsv("001", "Sportsbet", "101", "1", "2", 120.00, -110.00);
            MoneyLineCsv moneyLineCsv3 = new MoneyLineCsv("001", "Bookmaker", "103", "1", "2", 110.00, -120.00);
            MoneyLineCsv moneyLineCsv4 = new MoneyLineCsv("002", "Pinnacle", "100", "3", "4", 160.00, -230.00);
            List<GameCsv> gameCsvList = Arrays.asList(gameCsv1, gameCsv2, gameCsv3);
            List<MoneyLineCsv> moneyLineCsvList = Arrays.asList(moneyLineCsv1, moneyLineCsv2, moneyLineCsv3, moneyLineCsv4);
            List<Game> games = Game.toGames(gameCsvList, moneyLineCsvList, "100");
            Assertions.assertEquals(2, games.size());
            Game game1 = games.get(0);
            Assertions.assertEquals("001", game1.getGameId());
            Assertions.assertEquals("Pinnacle", game1.getBookName());
            Assertions.assertEquals("1", game1.getTeamId1());
            Assertions.assertEquals("2", game1.getTeamId2());
            Assertions.assertEquals(110.00, game1.getUsOdds1());
            Assertions.assertEquals(-110.00, game1.getUsOdds2());
            Assertions.assertTrue(game1.isHome());
            Assertions.assertTrue(game1.isWin());
            Game game2 = games.get(1);
            Assertions.assertEquals("002", game2.getGameId());
            Assertions.assertEquals("Pinnacle", game2.getBookName());
            Assertions.assertEquals("3", game2.getTeamId1());
            Assertions.assertEquals("4", game2.getTeamId2());
            Assertions.assertEquals(160.00, game2.getUsOdds1());
            Assertions.assertEquals(-230.00, game2.getUsOdds2());
            Assertions.assertFalse(game2.isHome());
            Assertions.assertFalse(game2.isWin());
        }

    }

}
