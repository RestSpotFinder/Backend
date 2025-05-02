package com.restspotfinder.restarea.collection;

import com.restspotfinder.restarea.domain.RestArea;
import com.restspotfinder.route.type.Direction;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record RestAreas(List<RestArea> restAreaList) {

    public Set<String> extractRouteNames() {
        return restAreaList.stream().map(RestArea::getRouteName).collect(Collectors.toSet());
    }

    public RestAreas filterAccessible(Map<String, Direction> directionMap) {
        List<RestArea> accessibleList = restAreaList.stream()
                .filter(restArea -> {
                    Direction direction = directionMap.get(restArea.getRouteName());
                    return restArea.isAccessible(direction);
                })
                .toList();

        return new RestAreas(accessibleList);
    }
}