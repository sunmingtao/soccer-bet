package com.smt.betfair.controller;

import com.smt.betfair.dto.response.Event;
import com.smt.betfair.service.BetfairApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/watch/{eventId}")
    public void watchEvent(@PathVariable long eventId) {
        betfairApiService.watchEvent(eventId);
    }

    @DeleteMapping("/unwatch/{eventId}")
    public void unwatchEvent(@PathVariable long eventId) {
        betfairApiService.unwatchEvent(eventId);
    }

}
