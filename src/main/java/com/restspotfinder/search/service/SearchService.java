package com.restspotfinder.search.service;

import com.restspotfinder.route.controller.request.RouteRequestDTO;
import com.restspotfinder.search.domain.Search;
import com.restspotfinder.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;

    public Search createByRoute(RouteRequestDTO routeRequestDTO) {
        Search search = Search.fromRoute(routeRequestDTO);
        return searchRepository.save(search);
    }

    public void createByRecent() {
        Search search = Search.fromSearch();
        searchRepository.save(search);
    }
}