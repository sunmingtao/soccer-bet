package com.smt.soccerbetrestapi.model.nba;

import lombok.Data;

import java.util.List;

@Data
public class NbaRawData {
    private List<NbaRawGame> games;
}
