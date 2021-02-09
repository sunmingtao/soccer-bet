package com.smt.betfair.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Params {
    private Filter filter = new Filter();
    private Integer maxResults;
    private List<String> marketIds = new ArrayList<>();
    private PriceProjection priceProjection = new PriceProjection();
}
