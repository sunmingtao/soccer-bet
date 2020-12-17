package com.smt.soccerbetrestapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum League {

    EPL("premier-league/"),
    LALIGA("la-liga/"),
    SERIE_A("serie-a/"),
    LIGUE_1("ligue-1/"),
    BUNDESLIGA("bundesliga/");

    private final String name;
}
