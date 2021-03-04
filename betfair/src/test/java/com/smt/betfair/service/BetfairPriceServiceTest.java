package com.smt.betfair.service;


import com.smt.betfair.dto.response.ApiResponse;
import com.smt.betfair.dto.response.Event;
import com.smt.betfair.dto.response.Result;
import com.smt.betfair.enums.MarketType;
import com.smt.betfair.model.Odds;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
public class BetfairPriceServiceTest {

    private static final long EVENT1_ID = 100L;
    private static final String EVENT_NAME = "man city vs man united";
    private static final String MARKET_ID = "123.45";
    private static final double WIN_ODDS = 1.3d;
    private static final double LOSS_ODDS = 6d;
    private static final double DRAW_ODDS = 4.5d;

    @Mock
    private BetfairApiClient betfairApiClient;
    @Mock
    private ApiResponse eventResponse;
    @Mock
    private ApiResponse marketIdResponse;
    @Mock
    private ApiResponse matchOddsResponse;
    @Mock
    private Result result1;
    @Mock
    private Event event1;

    private BetfairPriceService betfairPriceService;

    @BeforeEach
    void setup() {
        betfairPriceService = new BetfairPriceService(betfairApiClient);
    }

    @Nested
    class TestFindLastTradedPrices {

        @BeforeEach
        void setup() {
            Mockito.when(betfairApiClient.findEventByEventId(EVENT1_ID)).thenReturn(eventResponse);
        }

        @Test
        void noEvent() {
            Mockito.when(eventResponse.getFirstResult()).thenReturn(Optional.empty());
            Assertions.assertNull(betfairPriceService.findLastTradedPrices(EVENT1_ID));
        }

        @Nested
        class WhenEventResponseIsNotEmpty {
            @BeforeEach
            void setup() {
                Mockito.when(eventResponse.getFirstResult()).thenReturn(Optional.of(result1));
                Mockito.when(result1.getEvent()).thenReturn(event1);
                Mockito.when(event1.getName()).thenReturn(EVENT_NAME);
                Mockito.when(betfairApiClient.findMarketId(EVENT1_ID, MarketType.MATCH_ODDS)).thenReturn(marketIdResponse);

            }

            @Test
            void noMarket() {
                Mockito.when(marketIdResponse.getFirstResult()).thenReturn(Optional.empty());
                Assertions.assertNull(betfairPriceService.findLastTradedPrices(EVENT1_ID));
            }

            @Nested
            class WhenMarketResponseExists {
                @BeforeEach
                void setup() {
                    Mockito.when(marketIdResponse.getFirstResult()).thenReturn(Optional.of(result1));
                    Mockito.when(result1.getMarketId()).thenReturn(MARKET_ID);
                    Mockito.when(betfairApiClient.findMatchOdds(MARKET_ID)).thenReturn(matchOddsResponse);
                }

                @Test
                void noPrice() {
                    Mockito.when(matchOddsResponse.getFirstResult()).thenReturn(Optional.empty());
                    Assertions.assertNull(betfairPriceService.findLastTradedPrices(EVENT1_ID));
                }

                @Test
                void hasPrice() {
                    Mockito.when(matchOddsResponse.getFirstResult()).thenReturn(Optional.of(result1));
                    Mockito.when(result1.getLastTradedPrice()).thenReturn(List.of(WIN_ODDS, LOSS_ODDS, DRAW_ODDS));
                    Odds odds = betfairPriceService.findLastTradedPrices(EVENT1_ID);
                    Assertions.assertEquals(EVENT1_ID, odds.getEventId());
                    Assertions.assertEquals(EVENT_NAME, odds.getEventName());
                    Assertions.assertEquals(WIN_ODDS, odds.getWinOdds());
                    Assertions.assertEquals(LOSS_ODDS, odds.getLossOdds());
                    Assertions.assertEquals(DRAW_ODDS, odds.getDrawOdds());
                }
            }
        }
    }
}
