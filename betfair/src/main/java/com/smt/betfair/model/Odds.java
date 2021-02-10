package com.smt.betfair.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Odds {
    private int eventId;
    private String eventName;
    private double lastMatched;
    private double back;
    private double lay;
}
