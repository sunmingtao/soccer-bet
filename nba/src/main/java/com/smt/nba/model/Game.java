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
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Game {
    private final String gameId;
    private final String bookName;
    private final String teamId1;
    private final String teamId2;
    private final double usOdds1;
    private final double usOdds2;
    private final boolean home;
    private final boolean win;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GameId=" + gameId + "，");
        sb.append("Book name=" + bookName + "，");
        sb.append("Team Id 1=" + teamId1 + "，");
        sb.append("Team Id 2=" + teamId2 + "，");
        sb.append("US odds 1=" + usOdds1 + "，");
        sb.append("US odds 2=" + usOdds2 + "，");
        sb.append("Decimal odds 1=" + getDecimalOdds1() + "，");
        sb.append("Decimal odds 2=" + getDecimalOdds2() + "，");
        sb.append("Is home=" + home + "，");
        sb.append("Is Team 1 win=" + win + "，");
        sb.append("Profit as favourite=" + getProfitOnFavourite() + "，");
        sb.append("Profit as underdog=" + getProfitOnUnderdog() + "，");
        return sb.toString();
    }

    public double getDecimalOdds1() {
        return usOddsToDecimalOdds(usOdds1);
    }

    public double getDecimalOdds2() {
        return usOddsToDecimalOdds(usOdds2);
    }

    public double getProfitOnUnderdog() {
        return isUnderdogWin() ? 100 * (getUnderdogOdds() - 1) : -100;
    }

    public double getProfitOnFavourite() {
        return isFavouriteWin() ? 100 * (getFavouriteOdds() - 1) : -100;
    }

    public boolean isUnderdogOddsBetween(double odds1, double odds2) {
        double underdogOdds = getUnderdogOdds();
        return odds1 <= underdogOdds && odds2 >= underdogOdds;
    }

    private double getUnderdogOdds() {
        return Math.max(getDecimalOdds1(), getDecimalOdds2());
    }

    private double getFavouriteOdds() {
        return Math.min(getDecimalOdds1(), getDecimalOdds2());
    }

    private boolean isUnderdogWin() {
        return (isTeam1Underdog() && win) || (isTeam2Underdog() && !win);
    }

    private boolean isFavouriteWin() {
        return !isUnderdogWin();
    }

    private boolean isTeam1Underdog() {
        return getDecimalOdds1() == getUnderdogOdds();
    }

    private boolean isTeam2Underdog() {
        return !isTeam1Underdog();
    }

    protected static double usOddsToDecimalOdds(double usOdds) {
        if (usOdds > 0) {
            return 1 + (usOdds / 100);
        }
        return 1 - (100 / usOdds);
    }

    public static Game getInstance(MoneyLineCsv moneyLineCsv, GameCsv gameCsv) {
        return new Game(
                moneyLineCsv.getGameId(), moneyLineCsv.getBookName(), gameCsv.getTeamId1(),
                gameCsv.getTeamId2(), moneyLineCsv.getPrice(gameCsv.getTeamId1()), moneyLineCsv.getPrice(gameCsv.getTeamId2()),
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
