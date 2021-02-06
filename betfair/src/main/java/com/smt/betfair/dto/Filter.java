package com.smt.betfair.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Filter {
    private List<Integer> eventTypeIds = List.of();
}
