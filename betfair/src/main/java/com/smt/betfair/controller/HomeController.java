package com.smt.betfair.controller;

import com.smt.betfair.service.BetfairApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final BetfairApiClient betfairApiClient;

    @GetMapping("/listAllEvents")
    public String listAllEvents() {
        return betfairApiClient.listAllEvents();
    }
}
