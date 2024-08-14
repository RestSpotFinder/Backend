package com.restspotfinder.restarea.service.impl;

import com.restspotfinder.interchange.service.InterchangeService;
import com.restspotfinder.restarea.domain.RestArea;
import com.restspotfinder.restarea.repository.RestAreaRepository;
import com.restspotfinder.restarea.service.RestAreaService;
import com.restspotfinder.route.domain.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestAreaServiceImpl implements RestAreaService {
    private final RestAreaRepository restAreaRepository;
    private final InterchangeService interchangeService;

    @Override
    public RestArea getOneById(long restAreaId) {
        return restAreaRepository.findById(restAreaId)
                .orElseThrow(() -> new NullPointerException("[RestArea] restAreaId : " + restAreaId));
    }

    @Override
    public List<RestArea> getListNearbyRoutes(Route route) {
        return restAreaRepository.findNearbyRoutes(route.getLineString(), 300);
    }

    @Override
    public List<RestArea> filterAccessibleRestAreas(Route route, List<RestArea> restAreaList) {
        Set<String> routeNameSet = restAreaList.stream().map(RestArea::getRouteName).collect(Collectors.toSet());
        Map<String, String> directionMap = routeNameSet.stream()
                .collect(Collectors.toMap(
                        routeName -> routeName,
                        routeName -> interchangeService.getDirectionByRoute(route, routeName)
                ));

        return restAreaList.stream()
                .filter(r -> {
                    String direction = directionMap.get(r.getRouteName());
                    return direction != null && (direction.equals("판별 불가")
                            || r.getRouteDirection().equals(direction)
                            || r.getRouteDirection().equals("양방향"));
                }).toList();
    }
}