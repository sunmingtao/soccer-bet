package com.smt.soccerbetrestapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MatchResult {

    WIN(2), DRAW(1), LOSS(0);

    private final int point;
}
