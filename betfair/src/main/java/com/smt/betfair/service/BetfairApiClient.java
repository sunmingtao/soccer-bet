package com.smt.betfair.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smt.betfair.dto.request.ApiRequest;
import com.smt.betfair.dto.response.ApiResponse;
import com.smt.betfair.enums.MarketType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BetfairApiClient {

    private static final String BETFAIR_API_URL = "https://docs.developer.betfair.com/visualisers/betting";

    private final LoginService loginService;

    @Value("${betfair.api.key}")
    private String apiKey;

    public String listEventsTypes() {
        RestTemplate restTemplate = new RestTemplate();
        String body = assembleRequestForListEventsTypes();
        HttpEntity<String> entity = new HttpEntity<>(body, assembleHeaders());
        return restTemplate.postForObject(BETFAIR_API_URL, entity, String.class);
    }

    public ApiResponse listEventsByEventTypeId(int eventTypeId) {
        RestTemplate restTemplate = new RestTemplate();
        String body = assembleRequestForListEvents(List.of(eventTypeId), List.of());
        HttpEntity<String> entity = new HttpEntity<>(body, assembleHeaders());
        return restTemplate.postForObject(BETFAIR_API_URL, entity, ApiResponse.class);
    }

    public ApiResponse findEventByEventId(long eventId) {
        RestTemplate restTemplate = new RestTemplate();
        String body = assembleRequestForListEvents(List.of(), List.of(eventId));
        HttpEntity<String> entity = new HttpEntity<>(body, assembleHeaders());
        return restTemplate.postForObject(BETFAIR_API_URL, entity, ApiResponse.class);
    }

    public ApiResponse findMarketId(long eventId, MarketType marketType) {
        RestTemplate restTemplate = new RestTemplate();
        String body = assembleRequestForFindMarketId(eventId, marketType);
        HttpEntity<String> entity = new HttpEntity<>(body, assembleHeaders());
        return restTemplate.postForObject(BETFAIR_API_URL, entity, ApiResponse.class);
    }

    public ApiResponse findMatchOdds(String marketId) {
        RestTemplate restTemplate = new RestTemplate();
        String body = assembleRequestForFindMatchOdds(marketId);
        HttpEntity<String> entity = new HttpEntity<>(body, assembleHeaders());
        return restTemplate.postForObject(BETFAIR_API_URL, entity, ApiResponse.class);
    }

    private HttpHeaders assembleHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authentication", loginService.login().getToken());
        headers.set("X-Application", apiKey);
        return headers;
    }

    @SneakyThrows(JsonProcessingException.class)
    private String assembleRequestForListEventsTypes() {
        ObjectMapper mapper = new ObjectMapper();
        ApiRequest request = new ApiRequest();
        request.setMethod("SportsAPING/v1.0/listEventTypes");
        return mapper.writeValueAsString(request);
    }

    @SneakyThrows(JsonProcessingException.class)
    private String assembleRequestForListEvents(List<Integer> eventTypeIds, List<Long> eventIds) {
        ObjectMapper mapper = new ObjectMapper();
        ApiRequest request = new ApiRequest();
        request.setMethod("SportsAPING/v1.0/listEvents");
        request.getParams().getFilter().setEventTypeIds(eventTypeIds);
        request.getParams().getFilter().setEventIds(eventIds);
        return mapper.writeValueAsString(request);
    }

    @SneakyThrows(JsonProcessingException.class)
    private String assembleRequestForFindMarketId(long eventId, MarketType marketType) {
        ObjectMapper mapper = new ObjectMapper();
        ApiRequest request = new ApiRequest();
        request.setMethod("SportsAPING/v1.0/listMarketCatalogue");
        request.getParams().getFilter().setEventIds(List.of(eventId));
        request.getParams().getFilter().setMarketTypeCodes(List.of(marketType.name()));
        request.getParams().setMaxResults(1);
        return mapper.writeValueAsString(request);
    }

    @SneakyThrows(JsonProcessingException.class)
    public String assembleRequestForFindMatchOdds(String marketId) {
        ObjectMapper mapper = new ObjectMapper();
        ApiRequest request = new ApiRequest();
        request.setMethod("SportsAPING/v1.0/listMarketBook");
        request.getParams().setMarketIds(List.of(marketId));
        request.getParams().getPriceProjection().setPriceData(List.of("EX_BEST_OFFERS"));
        return mapper.writeValueAsString(request);
    }
}

