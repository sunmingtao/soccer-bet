package com.smt.betfair.dto.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Runner {
    private long selectionId;
    private String status;
    private double lastPriceTraded;
    private Ex ex;
}

