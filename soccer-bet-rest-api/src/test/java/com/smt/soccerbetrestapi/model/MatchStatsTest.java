package com.smt.soccerbetrestapi.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

@RunWith(JUnitPlatform.class)
public class MatchStatsTest {

    private MatchStats matchStats1;
    private MatchStats matchStats2;
    private MatchStats matchStats3;
    private MatchStats matchStats4;
    private MatchStats matchStats5;
    private MatchStats matchStats6;

    @BeforeEach
    void setup() {
        matchStats1 = new MatchStats(null, true, 1, 0.5, 0.25);
        matchStats2 = new MatchStats(null, true, 2, 0.6, 0.2);
        matchStats3 = new MatchStats(null, true, 2, 0.7, 0.2);
        matchStats4 = new MatchStats(null, true, 2, 0.8, 0.2);
        matchStats5 = new MatchStats(null, true, 2, 0.4, 0.2);
        matchStats6 = new MatchStats(null, true, 2, 0.3, 0.2);

        matchStats2.setPrev(matchStats1);
        matchStats3.setPrev(matchStats2);
        matchStats4.setPrev(matchStats3);
        matchStats5.setPrev(matchStats4);
        matchStats6.setPrev(matchStats5);
    }

    @Test
    void getExpectedPoints() {
        Assertions.assertEquals(1.25, matchStats1.getExpectedPoints());
    }

    @Test
    void getPointsDifference() {
        Assertions.assertEquals(-0.25, matchStats1.getPointsDifference());
        Assertions.assertEquals(0.6, matchStats2.getPointsDifference());
        Assertions.assertEquals(0.4, matchStats3.getPointsDifference());
        Assertions.assertEquals(0.2, matchStats4.getPointsDifference());
        Assertions.assertEquals(1, matchStats5.getPointsDifference());
        Assertions.assertEquals(1.2, matchStats6.getPointsDifference());
    }

    @Test
    void getAccumulativePointsDiff() {
        Assertions.assertEquals(-0.25, matchStats1.getAccumulativePointsDiff());
        Assertions.assertEquals(0.35, matchStats2.getAccumulativePointsDiff());
        Assertions.assertEquals(0.75, matchStats3.getAccumulativePointsDiff());
        Assertions.assertEquals(0.95, matchStats4.getAccumulativePointsDiff());
        Assertions.assertEquals(1.95, matchStats5.getAccumulativePointsDiff());
        Assertions.assertEquals(3.15, matchStats6.getAccumulativePointsDiff());
    }

    @Test
    void getLastNMatchesPointsDiff() {
        Assertions.assertEquals(-0.25, matchStats1.getLastNMatchesPointsDiff(3));
        Assertions.assertEquals(0.35, matchStats2.getLastNMatchesPointsDiff(3));
        Assertions.assertEquals(0.75, matchStats3.getLastNMatchesPointsDiff(3));
        Assertions.assertEquals(1.2, matchStats4.getLastNMatchesPointsDiff(3));
        Assertions.assertEquals(1.6, matchStats5.getLastNMatchesPointsDiff(3));
        Assertions.assertEquals(2.4, matchStats6.getLastNMatchesPointsDiff(3));
    }
}
