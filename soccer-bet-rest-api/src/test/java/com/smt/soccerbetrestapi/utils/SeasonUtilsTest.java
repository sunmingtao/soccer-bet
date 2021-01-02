package com.smt.soccerbetrestapi.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
class SeasonUtilsTest {
    @Test
    void toFullDate() {
        Assertions.assertEquals(SeasonUtils.fromFullDateString("2020/08/01"), SeasonUtils.toFullDate("8/1", "2020/08"));
        Assertions.assertEquals(SeasonUtils.fromFullDateString("2020/12/01"), SeasonUtils.toFullDate("12/1", "2020/08"));
        Assertions.assertEquals(SeasonUtils.fromFullDateString("2021/01/01"), SeasonUtils.toFullDate("1/1", "2020/08"));
        Assertions.assertEquals(SeasonUtils.fromFullDateString("2021/07/01"), SeasonUtils.toFullDate("7/1", "2020/08"));
    }

    @Test
    void getSeason() {
        Assertions.assertEquals("2020/2021", SeasonUtils.getSeason("2020/08"));
    }
}
