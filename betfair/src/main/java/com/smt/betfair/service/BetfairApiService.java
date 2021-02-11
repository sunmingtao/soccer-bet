package com.smt.betfair.service;

import com.smt.betfair.dto.response.ApiResponse;
import com.smt.betfair.enums.MarketType;
import com.smt.betfair.model.Odds;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BetfairApiService {
    private final BetfairApiClient betfairApiClient;

    public Odds findLastTradedPrices(int eventId) {
        ApiResponse eventResponse = betfairApiClient.findEventByEventId(eventId);
        String eventName = eventResponse.getFirstResult().getEvent().getName();
        ApiResponse marketIdResponse = betfairApiClient.findMarketId(eventId, MarketType.MATCH_ODDS);
        String marketId = marketIdResponse.getFirstResult().getMarketId();
        ApiResponse matchOddsResponse = betfairApiClient.findMatchOdds(marketId);
        List<Double> lastTradedPrice = matchOddsResponse.getFirstResult().getLastTradedPrice();
        return new Odds(eventId, eventName, lastTradedPrice.get(0), lastTradedPrice.get(1), lastTradedPrice.get(2));
    }
}
