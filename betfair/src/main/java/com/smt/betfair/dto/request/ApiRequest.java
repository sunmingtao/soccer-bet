package com.smt.betfair.dto.request;


import lombok.Data;

@Data
public class ApiRequest {
    private String method;
    private Params params = new Params();
}
