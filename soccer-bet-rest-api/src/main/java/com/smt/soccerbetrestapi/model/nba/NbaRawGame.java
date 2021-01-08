package com.smt.soccerbetrestapi.model.nba;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smt.soccerbetrestapi.entity.Match;
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
    private double prob1;
    @JsonProperty("rating_prob2")
    private double prob2;

    @JsonIgnore
    private String season;

    public boolean isCompleted() {
        return StringUtils.equalsIgnoreCase(status, "post");
    }

    public NbaGame toEntity() {
        return new NbaGame(id, date, team1, team2, score1, score2, prob1, prob2, season);
    }
}
