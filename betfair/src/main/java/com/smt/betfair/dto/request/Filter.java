package com.smt.betfair.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class Filter {
    private List<Integer> eventTypeIds;
    private List<Long> eventIds;
    private List<String> marketTypeCodes;
}
