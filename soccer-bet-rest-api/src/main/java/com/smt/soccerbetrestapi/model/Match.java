package com.smt.soccerbetrestapi.model;

import com.smt.soccerbetrestapi.enums.MatchResult;
import lombok.Value;

@Value
public class Match {

    private final Team homeTeam;
    private final Team awayTeam;
    private final MatchResult matchResult;
    private final double winProb;
    private final double drawProd;

}
