package com.restspotfinder.route.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.restspotfinder.route.domain.RouteOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class NaverGeoService {
    @Value("${naver.cloud-platform.direct5-url}")
    String DIRECT5_URL;
    @Value("${naver.cloud-platform.client-id}")
    String CLIENT_ID;
    @Value("${naver.cloud-platform.client-secret}")
    String CLIENT_SECRET;

    public Map<RouteOption, Coordinate[]> getRouteData(String start, String goal) {
        List<String> optionList = List.of(RouteOption.optimal.getValue(), RouteOption.fast.getValue(), RouteOption.avoidtoll.getValue());
        String requestURL = DIRECT5_URL + "?start=" + start + "&goal=" + goal + "&option=" + String.join(":", optionList);
        JsonNode jsonNode = connect(requestURL);
//        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));

        // 경로 데이터 추출
        return Map.of(
                RouteOption.fast, extractRoute(jsonNode, RouteOption.fast),
                RouteOption.optimal, extractRoute(jsonNode, RouteOption.optimal),
                RouteOption.avoidtoll, extractRoute(jsonNode, RouteOption.avoidtoll));
    }

    private Coordinate[] extractRoute(JsonNode jsonNode, RouteOption routeOption) {
        ArrayNode pathNode = (ArrayNode) jsonNode.get("route").get(routeOption.getValue()).get(0).get("path");
        List<Coordinate> route = new ArrayList<>();
        for (JsonNode path : pathNode) {
            double x = path.get(0).asDouble();
            double y = path.get(1).asDouble();
            route.add(new Coordinate(x, y));
        }
        return route.toArray(new Coordinate[0]);
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
