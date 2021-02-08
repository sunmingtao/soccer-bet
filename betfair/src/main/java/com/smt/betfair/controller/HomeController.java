package com.smt.betfair.controller;

import com.smt.betfair.dto.response.ApiResponse;
import com.smt.betfair.service.BetfairApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final BetfairApiClient betfairApiClient;

    @GetMapping("/listEventsTypes")
    public String listEventTypes() {
        return betfairApiClient.listEventsTypes();
    }

    @GetMapping("/listEvents/{eventTypeId}")
    public ApiResponse listEvents(@PathVariable int eventTypeId) {
        return betfairApiClient.listEvents(eventTypeId);
    }

    @GetMapping("/findMarketId/{eventId}/{marketType}")
    public String findMarketId(@PathVariable int eventId, @PathVariable String marketType) {
        return betfairApiClient.findMarketId(eventId, marketType);
    }
}
