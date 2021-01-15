package com.smt.soccerbetrestapi.model;

import com.smt.soccerbetrestapi.utils.DoubleUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class TeamList {
    private final List<Team> teams;

    public double getTotalProfitForLay() {
        double result = teams.stream().map(Team::getTotalProfit).reduce(0d, Double::sum);
        return DoubleUtils.round(result, 2);
    }

    public double getTotalProfitForLayAsFav() {
        double result = teams.stream().map(Team::getTotalProfitAsFav).reduce(0d, Double::sum);
        return DoubleUtils.round(result, 2);
    }

    public double getTotalProfitForLayAsStrictFav() {
        double result = teams.stream().map(Team::getTotalProfitAsStrictFav).reduce(0d, Double::sum);
        return DoubleUtils.round(result, 2);
    }

    public double getTotalProfitForBackDraw() {
        double result = teams.stream().map(Team::getTotalProfitBackOnDraw).reduce(0d, Double::sum) / 2;
        return DoubleUtils.round(result, 2);
    }

    public double getTotalProfitForLayDrawFixedWin() {
        double result = teams.stream().flatMap(team -> team.getMatchStatsList().stream())
                .map(MatchStats::getProfitLayOnDrawFixedWin).reduce(0d, Double::sum) / 2;
        return DoubleUtils.round(result, 2);
    }

    public double getTotalProfitForLayDrawFixedLiability() {
        double result = teams.stream().flatMap(team -> team.getMatchStatsList().stream())
                .map(MatchStats::getProfitLayOnDrawFixedLiability).reduce(0d, Double::sum) / 2;
        return DoubleUtils.round(result, 2);
    }

    public int getTotalDrawCount() {
        return (int)teams.stream().flatMap(team -> team.getMatchStatsList().stream())
                .filter(matchStats -> matchStats.getActualPoints() == 1).count() / 2;
    }

    public int getTotalMatchCount() {
        return (int)teams.stream().flatMap(team -> team.getMatchStatsList().stream()).count() / 2;
    }

    public int getTotalGoalCount() {
        return teams.stream().map(Team::getTotalGoal).reduce(0, Integer::sum);
    }
}
