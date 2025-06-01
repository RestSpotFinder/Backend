package com.restspotfinder.place.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.restspotfinder.apicount.service.ApiCountService;
import com.restspotfinder.place.domain.NaverPlace;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NaverPlaceService {
    private final ApiCountService apiCountService;
    @Value("${naver.developers.search-url}")
    String SEARCH_URL;
    @Value("${naver.developers.client-id}")
    String CLIENT_ID;
    @Value("${naver.developers.client-secret}")
    String CLIENT_SECRET;

    // 일일 허용량 25,000 건
    public void checkPlaceSearchCount() {
        // 일일 한도 25,000 건
        apiCountService.checkPlaceSearchCount(LocalDate.now());
    }
    @Transactional
    public List<NaverPlace> getPlaceListBySearchTerm(String searchTerm) {
        String requestURL = SEARCH_URL + "?display=5&query=" + searchTerm;
        JsonNode jsonNode = connect(requestURL);

        return NaverPlace.fromArray((ArrayNode) jsonNode.get("items"));
    }

    public JsonNode connect(String requestURL) {
        try {
            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
            RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", CLIENT_ID);
            headers.set("X-Naver-Client-Secret", CLIENT_SECRET);

            HttpEntity<Object> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestURL, HttpMethod.GET, entity, String.class);

            return new ObjectMapper().readTree(responseEntity.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}