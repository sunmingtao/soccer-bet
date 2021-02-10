package com.smt.betfair.service;

import com.smt.betfair.model.Odds;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BetfairApiService {
    private final BetfairApiClient betfairApiClient;

    public Odds getOdds(int eventId) {
        return new Odds(); //TODO
    }
}
