package com.restspotfinder.restarea.service;

import com.restspotfinder.restarea.collection.RestAreas;
import com.restspotfinder.restarea.domain.RestArea;
import com.restspotfinder.route.domain.Route;


public interface RestAreaService {
    RestArea getOneById(long restAreaId);

    RestAreas getAccessibleRestAreas(Route route);
}