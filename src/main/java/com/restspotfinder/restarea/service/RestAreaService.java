package com.restspotfinder.restarea.service;

import com.restspotfinder.interchange.service.InterchangeService;
import com.restspotfinder.restarea.domain.RestArea;
import com.restspotfinder.restarea.repository.RestAreaRepository;
import com.restspotfinder.route.domain.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestAreaService {
    private final RestAreaRepository restAreaRepository;
    private final InterchangeService interchangeService;

    public List<RestArea> getListNearbyRoutes(Route route) {
        return restAreaRepository.findNearbyRoutes(route.getLineString(), 300);
    }

    public RestArea getOneById(long restAreaId) {
        return restAreaRepository.findById(restAreaId)
                .orElseThrow(() -> new NullPointerException("[RestArea] restAreaId : " + restAreaId));
    }

    public List<RestArea> filterAccessibleRestAreas(Route route, List<RestArea> initList) {
        Map<String, List<RestArea>> groupingMap = RestArea.listToGroupingRouteNameMap(initList);
        Map<String, String> directionMap = groupingMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> interchangeService.getDirectionByRoute(route, entry.getKey())));

        return initList.stream()
                .filter(r -> {
                    String direction = directionMap.get(r.getRouteName());
                    return direction != null && (direction.equals("판별 불가") || r.getRouteDirection().equals(direction) || r.getRouteDirection().equals("양방향"));
                }).toList();
    }
}