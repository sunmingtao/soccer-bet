package com.smt.betfair.cron;

import com.smt.betfair.service.BetfairApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OddsLoader {

    private final BetfairApiService betfairApiService;

    @Scheduled(cron = "${load.odds.job.cron}")
    public void load() {
        betfairApiService.loadAndPersistOddsForUnderWatchEvents();
    }

}
