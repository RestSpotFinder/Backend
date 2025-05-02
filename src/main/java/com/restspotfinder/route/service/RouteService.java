package com.restspotfinder.route.service;

import com.restspotfinder.apicount.service.ApiCountService;
import com.restspotfinder.route.collection.Routes;
import com.restspotfinder.route.controller.request.RouteRequestDTO;
import com.restspotfinder.route.domain.NaverRoute;
import com.restspotfinder.route.domain.Route;
import com.restspotfinder.route.repository.RouteRepository;
import com.restspotfinder.search.domain.Search;
import com.restspotfinder.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final SearchRepository searchRepository;
    private final NaverRouteService naverRouteService;
    private final ApiCountService apiCountService;

    @Transactional
    public Routes create(RouteRequestDTO routeRequestDTO) {
        // 월간 한도 60,000 건
        apiCountService.checkRoutSearchCount(LocalDate.now());

        List<NaverRoute> naverRouteList = naverRouteService.getRouteData(routeRequestDTO);
        Search search = searchRepository.save(Search.fromRequestDTO(routeRequestDTO));

        List<Route> routeList = Route.fromList(naverRouteList, search.getSearchId(), routeRequestDTO);
        List<Route> savedRouteList = routeRepository.saveAll(routeList);

        return new Routes(savedRouteList);
    }

    @Transactional
    public Route getOneById(long routeId){
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new NullPointerException("[Route] routeId : " + routeId));
    }

    @Transactional
    public Routes getListBySearchId(long searchId) {
        List<Route> routeList = routeRepository.findBySearchId(searchId);
        Routes routes = new Routes(routeList);

        Search search = Search.fromRoutes(routes);
        searchRepository.save(search);

        return routes;
    }
}