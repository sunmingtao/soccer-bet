package com.smt.betfair.dto.response;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@ToString
public class ApiResponse {
    private String jsonrpc;
    private List<Result> result = new ArrayList<>();

    public Optional<Result> getFirstResult() {
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
