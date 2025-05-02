package com.restspotfinder.restarea.service.impl;

import com.restspotfinder.interchange.service.InterchangeService;
import com.restspotfinder.restarea.collection.RestAreas;
import com.restspotfinder.restarea.domain.RestArea;
import com.restspotfinder.restarea.repository.RestAreaRepository;
import com.restspotfinder.restarea.service.RestAreaService;
import com.restspotfinder.route.type.Direction;
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
    public RestAreas getAccessibleRestAreas(Route route) {
        List<RestArea> restAreaList = restAreaRepository.findNearbyRoutes(route.getLineString(), 300);
        RestAreas restAreas = new RestAreas(restAreaList);

        Set<String> routeNameSet = restAreas.extractRouteNames();
        Map<String, Direction> directionMap  = routeNameSet.stream()
                        .collect(Collectors.toMap(
                                routeName -> routeName,
                                routeName -> interchangeService.getDirectionByRoute(route, routeName)));

        return restAreas.filterAccessible(directionMap);
    }
}