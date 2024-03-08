package com.restspotfinder.route.service;

import com.restspotfinder.route.domain.NaverRoute;
import com.restspotfinder.route.domain.Route;
import com.restspotfinder.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;

    @Transactional
    public List<Route> create(List<NaverRoute> naverRouteList, long searchId) {
        return routeRepository.saveAll(Route.fromList(naverRouteList, searchId));
    }
}