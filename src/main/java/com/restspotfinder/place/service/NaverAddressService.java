package com.restspotfinder.place.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restspotfinder.place.domain.NaverPlace;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NaverAddressService {
    private final HierarchicalBeanFactory hierarchicalBeanFactory;
    @Value("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=")
    String REQUEST_URL;
    @Value("${naver.cloud-platform.client-id}")
    String CLOUD_CLIENT_ID;
    @Value("${naver.cloud-platform.client-secret}")
    String CLOUD_CLIENT_SECRET;

    // 일일 허용량 25,000 건

    @Transactional
    public List<NaverPlace> getPlaceListByAddress(String address) {
        String requestURL = REQUEST_URL + address;
        JsonNode jsonNode = connect_cloud(requestURL);
        for (JsonNode node : jsonNode) {
            System.out.println("node = " + node);
        }

        return new ArrayList<>();

//        return NaverPlace.fromArray((ArrayNode) jsonNode.get("items"));
    }

    public JsonNode connect_cloud(String requestURL) {
        try {
            System.out.println("requestURL = " + requestURL);
            System.out.println("CLOUD_CLIENT_ID = " + CLOUD_CLIENT_ID);
            System.out.println("CLOUD_CLIENT_SECRET = " + CLOUD_CLIENT_SECRET);
            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
            RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-ncp-apigw-api-key-id", CLOUD_CLIENT_ID);
            headers.set("x-ncp-apigw-api-key", CLOUD_CLIENT_SECRET);

            HttpEntity<Object> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestURL, HttpMethod.GET, entity, String.class);

            return new ObjectMapper().readTree(responseEntity.getBody());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}


