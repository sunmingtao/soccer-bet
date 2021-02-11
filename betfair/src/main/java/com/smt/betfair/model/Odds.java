package com.smt.betfair.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class Odds {
    private final int eventId;
    private final String eventName;
    private final double winOdds;
    private final double lossOdds;
    private final double drawOdds;
}
