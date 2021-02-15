package com.smt.betfair.controller;

import com.smt.betfair.dto.response.ApiResponse;
import com.smt.betfair.dto.response.LoginResponse;
import com.smt.betfair.enums.MarketType;
import com.smt.betfair.model.Odds;
import com.smt.betfair.repo.TimedOddsRepo;
import com.smt.betfair.service.BetfairApiClient;
import com.smt.betfair.service.BetfairApiService;
import com.smt.betfair.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final BetfairApiClient betfairApiClient;
    private final BetfairApiService betfairApiService;
    private final LoginService loginService;
    private final TimedOddsRepo timedOddsRepo;

    @GetMapping("/eventsTypes")
    public String listEventTypes() {
        return betfairApiClient.listEventsTypes();
    }

    @GetMapping("/events/{eventTypeId}")
    public ApiResponse listEvents(@PathVariable int eventTypeId) {
        return betfairApiClient.listEventsByEventTypeId(eventTypeId);
    }

    /**
     * e.g. http://localhost:8080/marketId/30267579/MATCH_ODDS
     */
    @GetMapping("/marketId/{eventId}/{marketType}")
    public ApiResponse findMarketId(@PathVariable int eventId, @PathVariable MarketType marketType) {
        return betfairApiClient.findMarketId(eventId, marketType);
    }

    /**
     * http://localhost:8080/matchOdds/1.178846026
     */
    @GetMapping("/matchOdds/{marketId}")
    public ApiResponse findMatchOdds(@PathVariable String marketId) {
        return betfairApiClient.findMatchOdds(marketId);
    }

    @GetMapping("lastTradedPrices/{eventId}")
    public Odds findLastTradedPrices(@PathVariable int eventId) {
        return betfairApiService.findLastTradedPrices(eventId);
    }

    @GetMapping("sessionToken")
    public LoginResponse getSessionToken() {
        return loginService.login();
    }
}
