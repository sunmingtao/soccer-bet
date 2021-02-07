package com.smt.betfair.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smt.betfair.dto.request.ApiRequest;
import com.smt.betfair.dto.response.ApiResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BetfairApiClient {

    private static final String BETFAIR_API_URL = "https://docs.developer.betfair.com/visualisers/betting";

    @Value("${betfair.session.token}")
    private String sessionToken;

    @Value("${betfair.api.key}")
    private String apiKey;

    public String listEventsTypes() {
        RestTemplate restTemplate = new RestTemplate();
        String body = assembleRequestForListEventsTypes();
        HttpEntity<String> entity = new HttpEntity<>(body, assembleHeaders());
        return restTemplate.postForObject(BETFAIR_API_URL, entity, String.class);
    }

    public ApiResponse listEvents(int eventId) {
        RestTemplate restTemplate = new RestTemplate();
        String body = assembleRequestForListEvents(eventId);
        HttpEntity<String> entity = new HttpEntity<>(body, assembleHeaders());
        return restTemplate.postForObject(BETFAIR_API_URL, entity, ApiResponse.class);
    }

    private HttpHeaders assembleHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authentication", sessionToken);
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
    private String assembleRequestForListEvents(int eventTypeId) {
        ObjectMapper mapper = new ObjectMapper();
        ApiRequest request = new ApiRequest();
        request.setMethod("SportsAPING/v1.0/listEvents");
        request.getParams().getFilter().setEventTypeIds(List.of(eventTypeId));
        return mapper.writeValueAsString(request);
    }
}

