package com.smt.soccerbetrestapi.model.nba;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smt.soccerbetrestapi.entity.NbaGame;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Data
public class NbaRawGame {
    private String id;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private String team1;
    private String team2;
    private int score1;
    private int score2;
    @JsonProperty("rating_prob1")
    private double raptorProb1;
    @JsonProperty("rating_prob2")
    private double raptorProb2;
    @JsonProperty("elo_prob1")
    private double eloProb1;
    @JsonProperty("elo_prob2")
    private double eloProb2;
    @JsonProperty("carmelo_prob1")
    private double legacyEloProb1;
    @JsonProperty("carmelo_prob2")
    private double legacyEloProb2;

    @JsonIgnore
    private String season;

    public boolean isCompleted() {
        return StringUtils.equalsIgnoreCase(status, "post");
    }

    public NbaGame toEntity() {
        return new NbaGame(id, date, team1, team2, score1, score2, raptorProb1, raptorProb2,
                eloProb1 > 0d ? eloProb1 : legacyEloProb1,
                eloProb2 > 0d ? eloProb2 : legacyEloProb2,
                season);
    }
}
