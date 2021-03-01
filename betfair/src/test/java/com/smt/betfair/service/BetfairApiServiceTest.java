package com.smt.betfair.service;

import com.smt.betfair.dto.response.ApiResponse;
import com.smt.betfair.dto.response.Event;
import com.smt.betfair.dto.response.Result;
import com.smt.betfair.entity.MatchUnderWatch;
import com.smt.betfair.repo.MatchUnderWatchRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Nested;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
class BetfairApiServiceTest {

    private static final long EVENT1_ID = 100L;
    private static final long EVENT2_ID = 200L;

    private BetfairApiService betfairApiService;

    @Mock
    private BetfairApiClient betfairApiClient;
    @Mock
    private MatchUnderWatchRepo matchUnderWatchRepo;
    @Mock
    private ApiResponse apiResponse;
    @Mock
    private Result result1;
    @Mock
    private Result result2;
    @Mock
    private Event event1;
    @Mock
    private Event event2;

    @BeforeEach
    void setup() {
        betfairApiService = new BetfairApiService(betfairApiClient, matchUnderWatchRepo);
    }

    @Nested
    class TestListEvents {
        @Test
        void test() {
            Mockito.when(betfairApiClient.listEventsByEventTypeId(1)).thenReturn(apiResponse);
            MatchUnderWatch matchUnderWatch = new MatchUnderWatch(EVENT1_ID);
            Mockito.when(matchUnderWatchRepo.findAll()).thenReturn(List.of(matchUnderWatch));
            Mockito.when(apiResponse.getResult()).thenReturn(List.of(result1, result2));
            Mockito.when(result1.getEvent()).thenReturn(event1);
            Mockito.when(result2.getEvent()).thenReturn(event2);
            Mockito.when(event1.getId()).thenReturn(EVENT1_ID);
            Mockito.when(event2.getId()).thenReturn(EVENT2_ID);
            List<Event> events = betfairApiService.listSoccerEvents();
            Assertions.assertEquals(2, events.size());
            Mockito.verify(event1, Mockito.times(1)).setWatch(true);
            Mockito.verify(event2, Mockito.times(0)).setWatch(true);
        }
    }
}
