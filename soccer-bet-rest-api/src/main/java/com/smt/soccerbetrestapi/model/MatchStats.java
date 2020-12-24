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

    public double getLiability() {
        return DoubleUtils.round((1/ winProb - 1) * 100, 2);
    }

    public double getProfit() {
        return actualPoints == 2 ? -getLiability() : 95;
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
