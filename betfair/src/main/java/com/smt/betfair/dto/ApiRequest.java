package com.smt.betfair.dto;


import lombok.Data;

//[{\"method\": \"SportsAPING/v1.0/listEventTypes\", \"params\": {\"filter\":{}}}]";
@Data
public class ApiRequest {
    private String method;
    private Params params = new Params();
}
