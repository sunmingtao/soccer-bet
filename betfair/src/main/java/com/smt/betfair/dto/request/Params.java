package com.smt.betfair.dto.request;

import lombok.Data;

@Data
public class Params {
    private Filter filter = new Filter();
    private Integer maxResults;
}
