package com.smt.soccerbetrestapi.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SeasonUtils {

    private static final FastDateFormat MATCH_DATE_FORMATTER = FastDateFormat.getInstance("MM/dd");
    private static final FastDateFormat SEASON_START_DATE_FORMATTER = FastDateFormat.getInstance("yyyy/MM");
    private static final FastDateFormat FULL_DATE_FORMATTER = FastDateFormat.getInstance("yyyy/MM/dd");

    /**
     * @param matchDate is in the format of "MM/dd"
     * @param seasonStartDate is in the format of "yyyy/MM"
     * @return
     */
    @SneakyThrows(ParseException.class)
    public static Date toFullDate(String matchDateStr, String seasonStartDateStr) {
        int matchMonth = getMonth(MATCH_DATE_FORMATTER.parse(matchDateStr));
        Date seasonStartDate = SEASON_START_DATE_FORMATTER.parse(seasonStartDateStr);
        int seasonStartMonth = getMonth(seasonStartDate);
        int seasonStartYear = getYear(seasonStartDate);
        int currentYear = matchMonth >= seasonStartMonth ? seasonStartYear : seasonStartYear + 1;
        return FULL_DATE_FORMATTER.parse(currentYear + "/" + matchDateStr);
    }

    @SneakyThrows(ParseException.class)
    public static Date fromFullDateString(String str) {
        return FULL_DATE_FORMATTER.parse(str);
    }

    public static String toFullDateString(Date date) {
        return FULL_DATE_FORMATTER.format(date);
    }

    private static int getMonth(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).getMonthValue();
    }

    private static int getYear(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).getYear();
    }
}
