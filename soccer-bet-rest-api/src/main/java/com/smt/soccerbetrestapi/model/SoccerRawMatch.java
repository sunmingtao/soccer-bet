package com.smt.soccerbetrestapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smt.soccerbetrestapi.entity.Match;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Getter @Setter
@ToString
public class SoccerRawMatch {
    private long id;
    @JsonProperty("datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date dateTime;
    private String team1;
    private String team2;
    private String status;
    @JsonProperty("team1_code")
    private String team1Code;
    @JsonProperty("team2_code")
    private String team2Code;
    @JsonProperty("prob1")
    private double team1WinProb;
    @JsonProperty("probtie")
    private double drawProb;
    private int score1;
    private int score2;

    @JsonIgnore
    private String leagueName;
    @JsonIgnore
    private String season;

    public boolean isCompleted() {
        return StringUtils.equalsIgnoreCase(status, "pre");
    }

    public Match toEntity() {
        return new Match(id, dateTime, team1, team2, team1Code, team2Code, score1, score2,
                team1WinProb, drawProb, leagueName, season);
    }
}
