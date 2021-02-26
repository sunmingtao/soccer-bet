package com.smt.betfair.dto.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Event {
    private int id;
    private String name;
    private String countryCode;
    private String openDate;
    private boolean watch;
}
