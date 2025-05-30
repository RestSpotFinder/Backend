package com.restspotfinder.restarea.service;

import com.restspotfinder.restarea.response.RestAreaResponse;
import java.util.List;


public interface RestAreaService {
    RestAreaResponse getOneById(long restAreaId);

    List<RestAreaResponse> getRestAreasWithPointCounts(long routeI);
}