package com.smt.betfair.dto.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginResponse {
    private String token;
    private String product;
}
