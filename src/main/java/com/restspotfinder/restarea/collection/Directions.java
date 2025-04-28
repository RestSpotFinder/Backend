package com.restspotfinder.restarea.collection;

import java.util.Map;

// RouteName, Direction
public record Directions(Map<String, String> directionMap) {

    public String getDirection(String routeName) {
        return directionMap.get(routeName);
    }
}