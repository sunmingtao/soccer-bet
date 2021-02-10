package com.smt.betfair.dto.response;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class Ex {
    private List<AvailableToBet> availableToBack = new ArrayList<>();
    private List<AvailableToBet> availableToLay = new ArrayList<>();
}

