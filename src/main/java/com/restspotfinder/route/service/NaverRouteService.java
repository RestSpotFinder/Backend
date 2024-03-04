package com.restspotfinder.route.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restspotfinder.route.domain.NaverRoute;
import com.restspotfinder.route.domain.RouteOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class NaverRouteService {
    @Value("${naver.cloud-platform.direct5-url}")
    String DIRECT5_URL;
    @Value("${naver.cloud-platform.client-id}")
    String CLIENT_ID;
    @Value("${naver.cloud-platform.client-secret}")
    String CLIENT_SECRET;

    public List<NaverRoute> getRouteData(String start, String goal, String waypoints) {
        String requestURL = DIRECT5_URL + "?start=" + start + "&goal=" + goal + "&waypoints=" + waypoints + "&option" + "=" + RouteOption.getCombinedValues();
        return NaverRoute.fromArray(connect(requestURL), RouteOption.values());
    }

    public JsonNode connect(String requestURL) {
        try {
            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
            RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-NCP-APIGW-API-KEY-ID", CLIENT_ID);
            headers.set("X-NCP-APIGW-API-KEY", CLIENT_SECRET);

            HttpEntity<Object> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestURL, HttpMethod.GET, entity, String.class);

            return new ObjectMapper().readTree(responseEntity.getBody());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
