package com.smt.betfair.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PriceProjection {
    private List<String> priceData = new ArrayList<>();
}
