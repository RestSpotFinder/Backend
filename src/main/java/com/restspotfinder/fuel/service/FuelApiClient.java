package com.restspotfinder.fuel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restspotfinder.fuel.domain.FuelStation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FuelApiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${ex.api.key}")
    private String API_KEY;
    private static final String API_URL = "https://data.ex.co.kr/openapi/business/curStateStation";

    public List<FuelStation> fetchAll() {
        String uri = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("key", API_KEY)
                .queryParam("type", "json")
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 999)
                .toUriString();

        List<FuelStation> result = new ArrayList<>();

        try {
            String response = restTemplate.getForObject(uri, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode list = root.path("list");

            if (list.isArray()) {
                for (JsonNode node : list) {
                    FuelStation station = new FuelStation(
                            node.path("serviceAreaCode").asText(),
                            node.path("serviceAreaName").asText(),
                            node.path("routeName").asText(),
                            node.path("direction").asText(),
                            node.path("gasolinePrice").asText(),
                            node.path("diselPrice").asText(),
                            node.path("lpgPrice").asText(),
                            node.path("oilCompany").asText(),
                            node.path("telNo").asText(),
                            node.path("svarAddr").asText()
                    );
                    result.add(station);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
