package com.restspotfinder.search.service;

import com.restspotfinder.search.domain.Search;
import com.restspotfinder.search.domain.SearchType;
import com.restspotfinder.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;

    public Search create(SearchType searchType) {
        return searchRepository.save(Search.from(searchType));
    }

    // 일일 호출량 계산
    public int countForPlace(LocalDate localDate) {
        return searchRepository.countForPlace(localDate);
    }
}