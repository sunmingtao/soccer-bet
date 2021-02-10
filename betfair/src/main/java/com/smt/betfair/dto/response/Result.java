package com.smt.betfair.dto.response;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class Result {
    private Event event;
    private int marketCount;
    private String marketId;
    private String marketName;
    private double totalMatched;
    private List<Runner> runners = new ArrayList<>();
}
