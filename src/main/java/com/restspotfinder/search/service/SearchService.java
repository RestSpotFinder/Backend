package com.restspotfinder.search.service;

import com.restspotfinder.search.domain.Search;
import com.restspotfinder.search.domain.SearchType;
import com.restspotfinder.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;

    public Search create(SearchType searchType) {
        return searchRepository.save(Search.from(searchType));
    }
}