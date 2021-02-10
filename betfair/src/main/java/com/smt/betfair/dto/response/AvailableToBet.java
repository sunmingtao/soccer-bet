package com.smt.betfair.dto.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AvailableToBet {
    private double price;
    private double size;
}
