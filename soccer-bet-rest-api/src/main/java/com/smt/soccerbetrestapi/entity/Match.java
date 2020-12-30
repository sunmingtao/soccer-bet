package com.smt.soccerbetrestapi.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.smt.soccerbetrestapi.converter.MatchDateCustomConverter;
import com.smt.soccerbetrestapi.model.MatchStats;
import com.smt.soccerbetrestapi.model.Team;
import com.smt.soccerbetrestapi.repo.TeamRepo;
import com.smt.soccerbetrestapi.utils.SeasonUtils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@DynamoDBTable(tableName = "soccer-bet")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Match {
    private Date matchDate;
    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;
    private double winProb;
    private double drawProb;
    private String league;

    public void setId(String id) {
        //Empty setter required by dynamodb
    }

    @DynamoDBHashKey
    @EqualsAndHashCode.Include
    public String getId() {
        return SeasonUtils.toFullDateString(matchDate) + "-" + homeTeam + "-" + awayTeam;
    }

    @DynamoDBTypeConverted(converter = MatchDateCustomConverter.class)
    public Date getMatchDate() {
        return matchDate;
    }

    @DynamoDBAttribute
    public String getHomeTeam() {
        return homeTeam;
    }

    @DynamoDBAttribute
    public String getAwayTeam() {
        return awayTeam;
    }

    @DynamoDBAttribute
    public int getHomeScore() {
        return homeScore;
    }

    @DynamoDBAttribute
    public int getAwayScore() {
        return awayScore;
    }

    @DynamoDBAttribute
    public double getWinProb() {
        return winProb;
    }

    @DynamoDBAttribute
    public double getDrawProb() {
        return drawProb;
    }

    @DynamoDBAttribute
    public String getLeague() {
        return league;
    }

    public void addToTeamMatchStats(TeamRepo teamRepo) {
        Team home = teamRepo.findOrCreate(homeTeam, league);
        Team away = teamRepo.findOrCreate(awayTeam, league);
        home.addMatchStats(toMatchStats(true));
        away.addMatchStats(toMatchStats(false));
    }

    private MatchStats toMatchStats(boolean home) {
        String opponent = home ? awayTeam : homeTeam;
        double localWinProb = home ? this.winProb : 1 - this.winProb - drawProb;
        return new MatchStats(opponent, home, getActualPoints(home), localWinProb, drawProb);
    }

    private int getActualPoints(boolean home) {
        int score1 = home ? homeScore : awayScore;
        int score2 = home ? awayScore : homeScore;
        if (score1 > score2) {
            return 2;
        }
        return score1 < score2 ? 0 : 1;
    }

    @Override
    public String toString() {
        return getId();
    }
}
