package com.smt.soccerbetrestapi.model;

import com.smt.soccerbetrestapi.utils.DoubleUtils;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class MatchStats extends LinkedNode<MatchStats> {

    private final Team opponent;
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

}
