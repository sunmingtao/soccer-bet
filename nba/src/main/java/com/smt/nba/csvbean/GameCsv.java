package com.smt.nba.csvbean;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GameCsv {
    @CsvBindByName(column = "game_id")
    private String gameId;

    @CsvBindByName(column = "game_date")
    private String gameDate;

    @CsvBindByName(column = "matchup")
    private String matchup;

    @CsvBindByName(column = "team_id")
    private String teamId1;

    @CsvBindByName(column = "a_team_id")
    private String teamId2;

    /**
     * t: home
     * f: away
     */
    @CsvBindByName(column = "is_home")
    private String homeAway;

    /**
     * W: win
     * L: away
     */
    @CsvBindByName(column = "wl")
    private String winLoss;

    @CsvBindByName
    private String season;

    public boolean isHome() {
        return "t".equalsIgnoreCase(homeAway);
    }

    public boolean isWin() {
        return "W".equalsIgnoreCase(winLoss);
    }
}
