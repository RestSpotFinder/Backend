package com.restspotfinder.restarea.service;

import com.restspotfinder.restarea.domain.RestArea;
import com.restspotfinder.restarea.repository.RestAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestAreaService {
    private final RestAreaRepository restAreaRepository;

    public List<RestArea> getListNearbyRoutes(long routeId) {
        return restAreaRepository.findNearbyRoutes(routeId);
    }

    public RestArea getOneById(long restAreaId) {
        return restAreaRepository.findById(restAreaId)
                .orElseThrow(() -> new NullPointerException("[RestArea] restAreaId : " + restAreaId));
    }
}