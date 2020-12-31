package com.smt.soccerbetrestapi.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum League {

    EPL("premier-league"),
    LALIGA("la-liga"),
    SERIE_A("serie-a"),
    LIGUE_1("ligue-1"),
    BUNDESLIGA("bundesliga"),
    CHINESE_SUPER_LEAGUE("chinese-super-league"),
    J_LEAGUE("j1-league"),
    EC("championship"),
    PORTUGAL("primeira-liga");

    private final String name;

    @JsonValue
    public String getName() {
        return name;
    }
}
