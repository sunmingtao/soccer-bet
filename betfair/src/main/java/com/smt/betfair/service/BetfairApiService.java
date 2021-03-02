package com.smt.betfair.service;

import com.smt.betfair.dto.response.ApiResponse;
import com.smt.betfair.dto.response.Event;
import com.smt.betfair.dto.response.Result;
import com.smt.betfair.entity.HistoryEvent;
import com.smt.betfair.entity.MatchUnderWatch;
import com.smt.betfair.enums.MarketType;
import com.smt.betfair.model.Odds;
import com.smt.betfair.repo.HistoryEventRepo;
import com.smt.betfair.repo.MatchUnderWatchRepo;
import com.smt.betfair.repo.TimedOddsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class BetfairApiService {
    private final BetfairApiClient betfairApiClient;
    private final MatchUnderWatchRepo matchUnderWatchRepo;
    private final HistoryEventRepo historyEventRepo;
    private final TimedOddsRepo timedOddsRepo;

    public Odds findLastTradedPrices(long eventId) {
        ApiResponse eventResponse = betfairApiClient.findEventByEventId(eventId);
        String eventName = eventResponse.getFirstResult().map(result -> result.getEvent().getName()).orElse(null);
        if (eventName == null) {
            return null;
        }
        ApiResponse marketIdResponse = betfairApiClient.findMarketId(eventId, MarketType.MATCH_ODDS);
        String marketId = marketIdResponse.getFirstResult().map(Result::getMarketId).orElse(null);
        if (marketId == null) {
            return null;
        }
        ApiResponse matchOddsResponse = betfairApiClient.findMatchOdds(marketId);
        List<Double> lastTradedPrice = matchOddsResponse.getFirstResult().map(Result::getLastTradedPrice).orElse(null);
        if (lastTradedPrice == null) {
            return null;
        }
        return new Odds(eventId, eventName, lastTradedPrice.get(0), lastTradedPrice.get(1), lastTradedPrice.get(2));
    }

    public List<Event> listSoccerEvents() {
        ApiResponse apiResponse = betfairApiClient.listEventsByEventTypeId(1);
        List<MatchUnderWatch> matchesUnderWatch = IterableUtils.toList(matchUnderWatchRepo.findAll());
        List<Event> events = apiResponse.getResult().stream().map(Result::getEvent).collect(Collectors.toList());
        events.stream().filter(event -> isUnderWatch(event.getId(), matchesUnderWatch)).forEach(event -> event.setWatch(true));
        return events;
    }

    private boolean isUnderWatch(long eventId, List<MatchUnderWatch> matchesUnderWatch) {
        return matchesUnderWatch.stream().anyMatch(m -> m.getEventIdAsLong() == eventId);
    }

    public void watchEvent(long eventId) {
        matchUnderWatchRepo.save(new MatchUnderWatch(eventId));
    }

    public void unwatchEvent(long eventId) {
        matchUnderWatchRepo.deleteById(String.valueOf(eventId));
    }

    public List<Long> listWatchEventsIds() {
        return StreamSupport.stream(matchUnderWatchRepo.findAll().spliterator(), false).map(MatchUnderWatch::getEventIdAsLong).collect(Collectors.toList());
    }

    public void loadAndPersistOddsForUnderWatchEvents() {
        listWatchEventsIds().forEach(this::loadAndPersistOdds);
    }

    private void loadAndPersistOdds(long eventId) {
        log.info("Load odds for event id {}", eventId);
        Optional.ofNullable(findLastTradedPrices(eventId))
                .map(Odds::toEntity).ifPresentOrElse(timedOddsRepo::save, () -> stopWatchAndAddToHistory(eventId));
    }

    private void stopWatchAndAddToHistory(long eventId) {
        unwatchEvent(eventId);
        if (!historyEventRepo.findById(String.valueOf(eventId)).isPresent()) {
            historyEventRepo.save(new HistoryEvent(eventId));
        }
    }
}
