package com.smt.soccerbetrestapi.model;

import com.smt.soccerbetrestapi.utils.DoubleUtils;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class MatchStats extends LinkedNode<MatchStats> {

    private final String opponent;
    private final boolean home;
    private final int actualPoint;
    private final double winProb;
    private final double drawProd;

    public double getExpectedPoints() {
        return DoubleUtils.round(winProb * 2 + drawProd, 2);
    }

    public double getPointsDifference() {
        return DoubleUtils.round(actualPoint - getExpectedPoints(), 2);
    }

    public double getAccumulativePointsDiff() {
        return DoubleUtils.round(getPointsDifference() + Optional.ofNullable(getPrev()).map(prev ->  prev.getAccumulativePointsDiff()).orElse(0d), 2);
    }

    public double getLastNMatchesPointsDiff(int n) {
        double result = Stream.iterate(this, Objects::nonNull, MatchStats::getPrev).limit(n)
                .reduce(0d, (partialResult, stat) -> partialResult + stat.getPointsDifference(), Double::sum);
        return DoubleUtils.round(result, 2);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Opponent: ").append(opponent);
        sb.append(" Home: ").append(home);
        sb.append(" Win Prob: ").append(DoubleUtils.round(winProb, 2));
        sb.append(" Draw Prob: ").append(DoubleUtils.round(drawProd, 2));
        sb.append(" Expected points: ").append(getExpectedPoints());
        sb.append(" Actual points: ").append(actualPoint);
        sb.append(" Points diff: ").append(getPointsDifference());
        sb.append(" Accumulative points diff: ").append(getAccumulativePointsDiff());
        sb.append(" Last 3 matches points diff: ").append(getLastNMatchesPointsDiff(3));
        sb.append(" Last 5 matches points diff: ").append(getLastNMatchesPointsDiff(5));
        return sb.toString();
    }
}
