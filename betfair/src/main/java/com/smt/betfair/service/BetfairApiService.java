package com.smt.betfair.service;

import com.smt.betfair.dto.response.ApiResponse;
import com.smt.betfair.dto.response.Event;
import com.smt.betfair.dto.response.Result;
import com.smt.betfair.entity.MatchUnderWatch;
import com.smt.betfair.enums.MarketType;
import com.smt.betfair.model.Odds;
import com.smt.betfair.repo.MatchUnderWatchRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.IterableUtils;

@Service
@RequiredArgsConstructor
public class BetfairApiService {
    private final BetfairApiClient betfairApiClient;
    private final MatchUnderWatchRepo matchUnderWatchRepo;

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
        List<Event> events = apiResponse.getResult().stream().map(Result::getEvent)
                .filter(event -> isUnderWatch(event.getId(), matchesUnderWatch)).collect(Collectors.toList());
        events.forEach(event -> event.setWatch(true));
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

}
