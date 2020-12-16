package com.smt.soccerbetrestapi.model;

import lombok.Value;

@Value
public class Match {

    private final String matchDate;
    private final Team homeTeam;
    private final Team awayTeam;
    private final int homeScore;
    private final int awayScore;
    private final double winProb;
    private final double drawProb;

    public void addToTeamMatchStats() {
        homeTeam.addMatchStats(toMatchStats(true));
        awayTeam.addMatchStats(toMatchStats(false));
    }

    private MatchStats toMatchStats(boolean home) {
        String opponent = home ? awayTeam.getName() : homeTeam.getName();
        double winProb = home ? this.winProb : 1 - this.winProb - drawProb;
        return new MatchStats(opponent, home, getActualPoints(home), winProb, drawProb);
    }

    private int getActualPoints(boolean home) {
        int score1 = home ? homeScore : awayScore;
        int score2 = home ? awayScore : homeScore;
        if (score1 > score2) {
            return 2;
        }
        return score1 < score2 ? 0 : 1;
    }


}
