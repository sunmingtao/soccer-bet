package com.smt.betfair.service;

import com.smt.betfair.dto.response.ApiResponse;
import com.smt.betfair.dto.response.Result;
import com.smt.betfair.enums.MarketType;
import com.smt.betfair.model.Odds;
import com.smt.betfair.repo.TimedOddsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BetfairApiService {
    private final BetfairApiClient betfairApiClient;

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

}
