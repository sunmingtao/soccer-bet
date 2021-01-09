package com.smt.soccerbetrestapi.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.smt.soccerbetrestapi.converter.MatchDateCustomConverter;
import lombok.*;

import java.util.Date;

@Data
@DynamoDBTable(tableName = "nba-game")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NbaGame {
    private String id;
    private Date date;
    private String team1;
    private String team2;
    private int score1;
    private int score2;
    private double prob1;
    private double prob2;
    private String season;

    @DynamoDBHashKey
    @EqualsAndHashCode.Include
    public String getId() {
        return id;
    }

    @DynamoDBTypeConverted(converter = MatchDateCustomConverter.class)
    public Date getDate() {
        return date;
    }

    public double getProfitFixedWinning() {
        boolean team1Dog = prob1 < prob2;
        boolean team1Win = score1 > score2;
        double favProb = Math.max(prob1, prob2);
        double win = 95d;
        double loss = (1 / favProb - 1) * 100d;
        boolean dogWin = (team1Dog && team1Win) || (!team1Dog && !team1Win);
        return dogWin ? win : -loss;
    }

    public void prettyPrint() {
        System.out.println(this.toString() + " Profit: " + getProfitFixedWinning());
    }

    public boolean isDogUnder(double prob) {
        return Math.min(prob1, prob2) < prob;
    }
}
