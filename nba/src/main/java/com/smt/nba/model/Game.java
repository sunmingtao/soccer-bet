package com.smt.nba.model;

import com.smt.nba.csvbean.GameCsv;
import com.smt.nba.csvbean.MoneyLineCsv;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Game {
    private final String gameId;
    private final String bookName;
    private final String teamId1;
    private final String teamId2;
    private final double usOdds1;
    private final double usOdds2;
    private final boolean home;
    private final boolean win;

    public double getDecimalOdds1() {
        return usOddsToDecimalOdds(usOdds1);
    }

    public double getDecimalOdds2() {
        return usOddsToDecimalOdds(usOdds2);
    }

    protected static double usOddsToDecimalOdds(double usOdds) {
        if (usOdds > 0) {
            return 1 + (usOdds / 100);
        }
        return 1 - (100 / usOdds);
    }

    public static Game getInstance(MoneyLineCsv moneyLineCsv, GameCsv gameCsv) {
        return new Game(
                moneyLineCsv.getGameId(), moneyLineCsv.getBookName(), moneyLineCsv.getTeamId1(),
                moneyLineCsv.getTeamId2(), moneyLineCsv.getPrice1(), moneyLineCsv.getPrice2(),
                gameCsv.isHome(), gameCsv.isWin()
        );
    }

    public static List<Game> toGames(List<GameCsv> gameCsvList, List<MoneyLineCsv> moneyLineCsvList, String bookId) {
        Map<String, MoneyLineGames> moneyLineGamesMap = MoneyLineGames.fromMoneyLineCsvList(moneyLineCsvList);
        return gameCsvList.stream().map(gameCsv -> fromCsv(gameCsv, moneyLineGamesMap, bookId))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static Game fromCsv(GameCsv gameCsv, Map<String, MoneyLineGames> moneyLineGamesMap, String bookId) {
        return Optional.ofNullable(moneyLineGamesMap.get(gameCsv.getGameId()))
                .flatMap(moneyLineGames -> moneyLineGames.findByBookId(bookId))
                .map(moneyLineCsv -> Game.getInstance(moneyLineCsv, gameCsv)).orElse(null);
    }
}
