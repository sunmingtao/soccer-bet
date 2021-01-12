package com.smt.soccerbetrestapi.model;

import com.smt.soccerbetrestapi.utils.DoubleUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class MatchStats extends LinkedNode<MatchStats> {

    private static final double COMMISSION = 0.05d;
    private static final double LIABILITY = 100d;

    private final String opponent;
    private final boolean home;
    private final int actualPoints;
    private final double winProb;
    private final double drawProb;

    public double getExpectedPoints() {
        return DoubleUtils.round(winProb * 2 + drawProb, 2);
    }

    public double getPointsDifference() {
        return DoubleUtils.round(actualPoints - getExpectedPoints(), 2);
    }

    public double getAccumulativePointsDiff() {
        return DoubleUtils.round(getPointsDifference() + Optional.ofNullable(getPrev())
                .map(MatchStats::getAccumulativePointsDiff).orElse(0d), 2);
    }

    public double getLast3MatchesPointsDiff() {
        return getLastNMatchesPointsDiff(3);
    }

    public double getLast5MatchesPointsDiff() {
        return getLastNMatchesPointsDiff(5);
    }

    protected double getLastNMatchesPointsDiff(int n) {
        double result = Stream.iterate(this, Objects::nonNull, MatchStats::getPrev).limit(n)
                .reduce(0d, (partialResult, stat) -> partialResult + stat.getPointsDifference(), Double::sum);
        return DoubleUtils.round(result, 2);
    }

    public double getDrawRate() {
        return getDrawRateForLastNMatches(100);
    }

    public double getDrawRateForLast10Matches() {
        return getDrawRateForLastNMatches(10);
    }

    private double getDrawRateForLastNMatches(int n) {
        long matchCount = Stream.iterate(this, Objects::nonNull, MatchStats::getPrev).limit(n).count();
        long drawCount = Stream.iterate(this, Objects::nonNull, MatchStats::getPrev).limit(n)
                .filter(matchStats -> matchStats.getActualPoints() == 1).count();
        return DoubleUtils.round(drawCount / (double) matchCount, 2);
    }

    public double getWinProb() {
        return DoubleUtils.round(winProb, 2);
    }

    public double getDrawProb() {
        return DoubleUtils.round(drawProb,2);
    }

    public String getHomeOrAway() {
        return home ? "Home" : "Away";
    }

    public String getFavouriteOrUnderDog() {
        return isFavourite() ? "Fav" : "Under";
    }

    private boolean isFavourite() {
        return 2 * winProb > 1  - drawProb;
    }

    public double getProfitFixedLiability() {
        double win = LIABILITY / (1 - winProb) - LIABILITY;
        double profit = actualPoints == 2 ? -LIABILITY : win * (1 - COMMISSION);
        return DoubleUtils.round(profit, 2);
    }

    public double getProfitFixedWin() {
        double win = LIABILITY;
        double loss = (1 / winProb - 1) * LIABILITY;
        double profit = actualPoints == 2 ? -loss : win * (1 - COMMISSION);
        return DoubleUtils.round(profit, 2);
    }

    public double getProfitBackOnDrawFixedLiability() {
        double win = LIABILITY / drawProb - LIABILITY;
        double profit = actualPoints == 1 ? win * (1 - COMMISSION) : -LIABILITY;
        return DoubleUtils.round(profit, 2);
    }

    public double getProfitBackOnDrawFixedWin() {
        double win = LIABILITY;
        double loss = (1 / (1 - drawProb) - 1) * LIABILITY;
        double profit = actualPoints == 1 ? win * (1 - COMMISSION) : -loss;
        return DoubleUtils.round(profit, 2);
    }

    public double getProfitLayOnDrawFixedWin() {
        double loss = (1 / drawProb - 1) * LIABILITY;
        double win = LIABILITY;
        double profit = actualPoints != 1 ? win * (1 - COMMISSION) : -loss;
        return DoubleUtils.round(profit, 2);
    }

    public double getProfitLayOnDrawFixedLiability() {
        double win = LIABILITY / (1 - drawProb) - LIABILITY;
        double profit = actualPoints != 1 ? win * (1 - COMMISSION) : -LIABILITY;
        return DoubleUtils.round(profit, 2);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Opponent: ").append(opponent);
        sb.append(" Home: ").append(home);
        sb.append(" Win Prob: ").append(DoubleUtils.round(winProb, 2));
        sb.append(" Draw Prob: ").append(DoubleUtils.round(drawProb, 2));
        sb.append(" Expected points: ").append(getExpectedPoints());
        sb.append(" Actual points: ").append(actualPoints);
        sb.append(" Points diff: ").append(getPointsDifference());
        sb.append(" Accumulative points diff: ").append(getAccumulativePointsDiff());
        sb.append(" Last 3 matches points diff: ").append(getLastNMatchesPointsDiff(3));
        sb.append(" Last 5 matches points diff: ").append(getLastNMatchesPointsDiff(5));
        return sb.toString();
    }
}
