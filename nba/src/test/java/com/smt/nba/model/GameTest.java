package com.smt.nba.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class GameTest {

    @Test
    void usOddsToDecimalOdds() {
        Assertions.assertEquals(2d, Game.usOddsToDecimalOdds(100));
        Assertions.assertEquals(2d, Game.usOddsToDecimalOdds(-100));
        Assertions.assertEquals(4d, Game.usOddsToDecimalOdds(300));
        Assertions.assertEquals(4d/3, Game.usOddsToDecimalOdds(-300));
    }
}
