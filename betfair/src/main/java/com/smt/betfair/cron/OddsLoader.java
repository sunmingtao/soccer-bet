package com.smt.betfair.cron;

import com.smt.betfair.entity.TimedOdds;
import com.smt.betfair.model.Odds;
import com.smt.betfair.repo.TimedOddsRepo;
import com.smt.betfair.service.BetfairApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OddsLoader {

    private static final List<Integer> EVENT_IDS = List.of(30186200, 30186226, 30186235);

    private final BetfairApiService betfairApiService;
    private final TimedOddsRepo timedOddsRepo;

    @Scheduled(cron = "${load.odds.job.cron}")
    public void load() {
        EVENT_IDS.forEach(this::loadAndPersistOdds);
    }

    private void loadAndPersistOdds(long eventId) {
        log.info("Load odds for event id {}", eventId);
        Optional.ofNullable(betfairApiService.findLastTradedPrices(eventId))
                .map(Odds::toEntity).ifPresent(timedOddsRepo::save);
    }
}
