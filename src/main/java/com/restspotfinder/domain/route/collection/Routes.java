package com.restspotfinder.domain.route.collection;

import com.restspotfinder.domain.route.entity.Route;

import java.util.List;

public record Routes(List<Route> routeList) {

    public Routes {
        if (routeList == null || routeList.isEmpty()) {
            throw new IllegalArgumentException("routeList는 비어 있을 수 없습니다.");
        }
    }

    public String getStartName() {
        return routeList.get(0).getStartName();
    }

    public String getGoalName() {
        return routeList.get(0).getGoalName();
    }
}