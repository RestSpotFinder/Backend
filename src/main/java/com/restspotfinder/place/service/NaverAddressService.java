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
        JsonNode jsonNode = connectCloud(requestURL);
//        for (JsonNode node : jsonNode) {
//            System.out.println("node = " + node);
//        }

        List<NaverPlace> naverPlaceList = new ArrayList<>();
        JsonNode addressesNode = jsonNode.get("addresses");

        if (addressesNode != null && addressesNode.isArray()) {
            for (JsonNode addressNode : addressesNode) {
                NaverPlace place = fromAddressNode(addressNode);
                naverPlaceList.add(place);
            }
        }

        return naverPlaceList;

//        return NaverPlace.fromArray((ArrayNode) jsonNode.get("items"));
    }

    private NaverPlace fromAddressNode(JsonNode node) {
        String title = node.has("roadAddress") && !node.get("roadAddress").asText().isEmpty()
                ? node.get("roadAddress").asText() : node.get("jibunAddress").asText();

        return NaverPlace.builder()
                .title(title)  // 도로명 또는 지번 주소 (사용자 입력 값)
                .mapX(node.get("x").asText())
                .mapY(node.get("y").asText())
                .category(null)
                .address(node.get("jibunAddress").asText())  // 지번 주소
                .roadAddress(node.get("roadAddress").asText())  // 도로명 주소
                .link(null)
                .description(null)
                .telephone(null)
                .build();
    }

    public JsonNode connectCloud(String requestURL) {
        try {
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


