package com.smt.nba.model;

import com.smt.nba.csvbean.MoneyLineCsv;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@Getter
public class MoneyLineGames {
    private final String gameId;
    private final List<MoneyLineCsv> moneyLineCsvList = new ArrayList<>();

    public Optional<MoneyLineCsv> findByBookId(String bookId) {
        return moneyLineCsvList.stream().filter(moneyLineCsv -> bookId.equals(moneyLineCsv.getBookId())).findFirst();
    }

    private void addGame(MoneyLineCsv moneyLineGame) {
        moneyLineCsvList.add(moneyLineGame);
    }

    public static Map<String, MoneyLineGames> fromMoneyLineCsvList(List<MoneyLineCsv> moneyLineCsvList) {
        Map<String, MoneyLineGames> moneyLineGamesMap = new HashMap<>();
        for (MoneyLineCsv moneyLineCsv : moneyLineCsvList) {
            MoneyLineGames moneyLineGames = Optional.ofNullable(moneyLineGamesMap.get(moneyLineCsv.getGameId()))
                    .orElse(new MoneyLineGames(moneyLineCsv.getGameId()));
            moneyLineGames.addGame(moneyLineCsv);
            moneyLineGamesMap.put(moneyLineCsv.getGameId(), moneyLineGames);
        }
        return moneyLineGamesMap;
    }
}
