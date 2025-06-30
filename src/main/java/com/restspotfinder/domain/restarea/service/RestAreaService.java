package com.restspotfinder.domain.restarea.service;

import com.restspotfinder.domain.restarea.dto.RestAreaResponse;
import java.util.List;


public interface RestAreaService {
    RestAreaResponse getOneById(long restAreaId);

    List<RestAreaResponse> getRestAreasWithPointCounts(long routeI);
}