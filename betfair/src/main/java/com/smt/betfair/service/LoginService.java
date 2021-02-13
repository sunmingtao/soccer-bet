package com.smt.betfair.service;

import com.smt.betfair.dto.response.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LoginService {

    private static final String BETFAIR_LOGIN_URL = "https://identitysso.betfair.com/api/login";

    @Value("${betfair.username}")
    private String username;

    @Value("${betfair.password}")
    private String password;

    @Value("${betfair.api.key}")
    private String apiKey;

    @Cacheable(value = "loginCache", key = "{#root.methodName}")
    public LoginResponse login() {
        RestTemplate restTemplate = new RestTemplate();
        String body = "username="+ username +"&password=" + password;
        HttpEntity<String> entity = new HttpEntity<>(body, assembleHeaders());
        return restTemplate.postForObject(BETFAIR_LOGIN_URL, entity, LoginResponse.class);
    }

    private HttpHeaders assembleHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("X-Application", apiKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}
