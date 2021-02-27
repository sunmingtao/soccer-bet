package com.smt.soccerbetrestapi.entity;

import com.smt.soccerbetrestapi.utils.SeasonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class MatchTest {
    @Test
    void equals() {
        Match match1 = new Match("1", SeasonUtils.fromFullDateString("2020/1/2"), "liverpool", "man city", null, null, 1, 0, 0.25d, 0.25d, "epl", "2020/2021");
        Match match2 = new Match("1", SeasonUtils.fromFullDateString("2020/1/2"), "liverpool", "man city", null, null, 1, 1, 0.25d, 0.25d, "epl", "2020/2021");
        Assertions.assertTrue(match1.equals(match2));
    }

    @Test
    void notEquals() {
        Match match1 = new Match("1", SeasonUtils.fromFullDateString("2020/1/2"), "liverpool", "man city", null, null, 1, 0, 0.25d, 0.25d, "epl", "2020/2021");
        Match match2 = new Match("2", SeasonUtils.fromFullDateString("2020/1/3"), "liverpool", "man city", null, null, 1, 0, 0.25d, 0.25d, "epl", "2020/2021");
        Assertions.assertFalse(match1.equals(match2));
    }
}
