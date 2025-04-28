package com.restspotfinder.restarea.collection;

import com.restspotfinder.restarea.domain.RestArea;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record RestAreas(List<RestArea> restAreaList) {

    public Set<String> extractRouteNames() {
        return restAreaList.stream().map(RestArea::getRouteName).collect(Collectors.toSet());
    }

    public RestAreas filterAccessible(Directions directions) {
        List<RestArea> accessibleList = restAreaList.stream()
                .filter(restArea -> {
                    String direction = directions.getDirection(restArea.getRouteName());
                    return restArea.isAccessible(direction);
                })
                .toList();

        return new RestAreas(accessibleList);
    }
}