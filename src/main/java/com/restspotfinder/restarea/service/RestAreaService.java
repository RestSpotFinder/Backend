package com.restspotfinder.restarea.service;

import com.restspotfinder.restarea.domain.RestArea;
import com.restspotfinder.route.domain.Route;

import java.util.List;


public interface RestAreaService {
    RestArea getOneById(long restAreaId);

    List<RestArea> getListNearbyRoutes(Route route);

    List<RestArea> filterAccessibleRestAreas(Route route, List<RestArea> initList);
}