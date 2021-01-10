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
    private double raptorProb1;
    private double raptorProb2;
    private double eloProb1;
    private double eloProb2;
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

    public double getProfitFixedWin(boolean raptor) {
        double prob1 = raptor ? raptorProb1 : eloProb1;
        double prob2 = raptor ? raptorProb2 : eloProb2;
        boolean team1Dog = prob1 < prob2;
        boolean team1Win = score1 > score2;
        double favProb = Math.max(prob1, prob2);
        double win = 95d;
        double loss = (1 / favProb - 1) * 100d;
        boolean dogWin = (team1Dog && team1Win) || (!team1Dog && !team1Win);
        return dogWin ? win : -loss;
    }

    public void prettyPrint(boolean raptor) {
        System.out.println(this.toString() + " Profit: " + getProfitFixedWin(raptor));
    }

    public boolean isDogUnder(double prob, boolean raptor) {
        double prob1 = raptor ? raptorProb1 : eloProb1;
        double prob2 = raptor ? raptorProb2 : eloProb2;
        return Math.min(prob1, prob2) < prob;
    }

    public boolean isRaptorEloConsistent() {
        boolean team1DogRaptor = raptorProb1 < raptorProb2;
        boolean team1DogElo = eloProb1 < eloProb2;
        return team1DogRaptor == team1DogElo;
    }
}
