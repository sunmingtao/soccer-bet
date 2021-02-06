package com.smt.betfair.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smt.betfair.dto.ApiRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BetfairApiClient {

    private static final String BETFAIR_API_URL = "https://docs.developer.betfair.com/visualisers/betting";

    @Value("${betfair.session.token}")
    private String sessionToken;

    @Value("${betfair.api.key}")
    private String apiKey;

    public String listAllEvents() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Authentication", sessionToken);
        headers.set("X-Application", apiKey);
        String body = getRequest();
        System.out.println("Request = " + body);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(BETFAIR_API_URL, entity, String.class);
    }

    @SneakyThrows(JsonProcessingException.class)
    private String getRequest() {
        ObjectMapper mapper = new ObjectMapper();
        ApiRequest request = new ApiRequest();
        request.setMethod("SportsAPING/v1.0/listEventTypes");
        return mapper.writeValueAsString(request);
    }
}

