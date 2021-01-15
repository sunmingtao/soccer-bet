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
    SCOTLAND("premiership"),
    EC("championship"),
    EL1("league-one"),
    EL2("league-two"),
    PORTUGAL("primeira-liga"),
    NETHERLANDS("eredivisie"),
    BELGIUM("first-division-a"),
    MEXICO("liga-mx"),
    LALIGA2("la-liga-2"),
    BUNDESLIGA2("bundesliga-2"),
    SERIE_B("serie-b");

    private final String name;

    @JsonValue
    public String getName() {
        return name;
    }
}
