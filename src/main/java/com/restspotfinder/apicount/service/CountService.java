package com.restspotfinder.apicount.service;

import com.restspotfinder.apicount.domain.PlaceSearchCount;
import com.restspotfinder.apicount.domain.RouteSearchCount;
import com.restspotfinder.apicount.repository.PlaceSearchCountRepository;
import com.restspotfinder.apicount.repository.RouteSearchCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CountService {
    private final PlaceSearchCountRepository placeSearchCountRepository;
    private final RouteSearchCountRepository routeSearchCountRepository;

    @Transactional
    public int increasePlaceSearchCount(LocalDate today) {
        PlaceSearchCount placeSearchCount = placeSearchCountRepository.findByDateWithPessimisticLock(today)
                .orElse(PlaceSearchCount.init(LocalDate.now()));
        int count = placeSearchCount.increase();

        placeSearchCountRepository.save(placeSearchCount);
        return count;
    }

    @Transactional
    public int increaseRouteSearchCount(LocalDate today) {
        LocalDate startOfMonth = today.withDayOfMonth(1);
        System.out.println("startOfMonth = " + startOfMonth);
        RouteSearchCount routeSearchCount = routeSearchCountRepository.findByDateWithPessimisticLock(startOfMonth);
        int count = routeSearchCount.increase();

        routeSearchCountRepository.save(routeSearchCount);
        return count;
    }
}