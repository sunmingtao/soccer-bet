package com.smt.betfair.model;

import com.smt.betfair.entity.TimedOdds;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

@Data
@ToString
@RequiredArgsConstructor
public class Odds {

    private static final FastDateFormat DATE_FORMATTER = FastDateFormat.getInstance("yyyy/MM/dd HH:mm");

    private final int eventId;
    private final String eventName;
    private final double winOdds;
    private final double lossOdds;
    private final double drawOdds;

    public TimedOdds toEntity() {
        Date now = new Date();
        String id = eventId + "-" + DATE_FORMATTER.format(now);
        return new TimedOdds(id, eventId, eventName, winOdds, lossOdds, drawOdds, now);
    }
}
