package com.smt.betfair.controller;

import com.smt.betfair.dto.response.ApiResponse;
import com.smt.betfair.enums.MarketType;
import com.smt.betfair.model.Odds;
import com.smt.betfair.service.BetfairApiClient;
import com.smt.betfair.service.BetfairApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final BetfairApiClient betfairApiClient;
    private final BetfairApiService betfairApiService;

    @GetMapping("/listEventsTypes")
    public String listEventTypes() {
        return betfairApiClient.listEventsTypes();
    }

    @GetMapping("/listEvents/{eventTypeId}")
    public ApiResponse listEvents(@PathVariable int eventTypeId) {
        return betfairApiClient.listEventsByEventTypeId(eventTypeId);
    }

    /**
     * e.g. http://localhost:8080/findMarketId/30267579/MATCH_ODDS
     */
    @GetMapping("/findMarketId/{eventId}/{marketType}")
    public ApiResponse findMarketId(@PathVariable int eventId, @PathVariable MarketType marketType) {
        return betfairApiClient.findMarketId(eventId, marketType);
    }

    /**
     * http://localhost:8080/findMatchOdds/1.178846026
     */
    @GetMapping("/findMatchOdds/{marketId}")
    public ApiResponse findMatchOdds(@PathVariable String marketId) {
        return betfairApiClient.findMatchOdds(marketId);
    }

    @GetMapping("lastTradedPrices/{eventId}")
    public Odds findLastTradedPrices(@PathVariable int eventId) {
        return betfairApiService.findLastTradedPrices(eventId);
    }
}
