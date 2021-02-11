package com.smt.betfair.dto.response;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
public class Result {
    private Event event;
    private int marketCount;
    private String marketId;
    private String marketName;
    private double totalMatched;
    private List<Runner> runners = new ArrayList<>();

    public List<Double> getLastTradedPrice() {
        return runners.stream().map(Runner::getLastPriceTraded).collect(Collectors.toList());
    }
}
