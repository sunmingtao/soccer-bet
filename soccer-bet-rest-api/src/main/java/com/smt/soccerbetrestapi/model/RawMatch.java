package com.smt.soccerbetrestapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class RawMatch {
    private long id;
    @JsonProperty("league_id")
    private int leagueId;
    @JsonProperty("datetime")
    private String dateTime;
    private String team1;
    private String team2;
    private String status;

}
