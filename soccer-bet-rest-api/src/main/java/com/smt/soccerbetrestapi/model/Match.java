package com.smt.soccerbetrestapi.model;

import com.smt.soccerbetrestapi.enums.MatchResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Match {

    private final Team homeTeam;
    private final Team awayTeam;
    private final MatchResult matchResult;
    private final int winProb;
    private final int drawProd;

    public double getExpectedPoints() {
        return 0.0;
    }

    private double getPointsDifferenceForHomeTeam() {
        return matchResult.getPoint() - getExpectedPoints();
    }

    public double getPointsDifference(boolean homeTeam) {
        return homeTeam ? getPointsDifferenceForHomeTeam() : -getPointsDifferenceForHomeTeam();
    }
}
