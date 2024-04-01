package com.restspotfinder.search.service;

import com.restspotfinder.search.domain.Search;
import com.restspotfinder.search.domain.SearchType;
import com.restspotfinder.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepository searchRepository;

    public Search create(SearchType searchType, Boolean isTest) {
        return searchRepository.save(Search.from(searchType, isTest));
    }

    // 일일 호출량 계산
    public int countForPlace(LocalDate localDate) {
        return searchRepository.countForPlace(localDate);
    }

    // 월간 호출량 계산
    public int countForRouteInMonth(LocalDate localDate) {
        YearMonth yearMonth = YearMonth.of(localDate.getYear(), localDate.getMonth()); // 예시: 2023년 5월
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        return searchRepository.countForRouteInMonth(firstDayOfMonth, lastDayOfMonth);
    }
}