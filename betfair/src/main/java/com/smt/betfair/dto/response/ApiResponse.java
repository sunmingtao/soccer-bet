package com.smt.betfair.dto.response;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class ApiResponse {
    private String jsonrpc;
    private List<Result> result = new ArrayList<>();
}
