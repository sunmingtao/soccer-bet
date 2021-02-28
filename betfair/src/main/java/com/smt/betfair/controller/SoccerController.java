package com.smt.betfair.controller;

import com.smt.betfair.dto.response.Event;
import com.smt.betfair.service.BetfairApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/soccer")
@RestController
@RequiredArgsConstructor
public class SoccerController {
    private final BetfairApiService betfairApiService;

    @GetMapping("/events")
    public List<Event> listEvents() {
        return betfairApiService.listSoccerEvents();
    }
}
