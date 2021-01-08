package com.smt.soccerbetrestapi.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.smt.soccerbetrestapi.converter.MatchDateCustomConverter;
import lombok.*;

import java.util.Date;

@Setter
@Getter
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
}
