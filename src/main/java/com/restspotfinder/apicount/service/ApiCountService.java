package com.restspotfinder.apicount.service;

import com.restspotfinder.apicount.domain.AddressSearchCount;
import com.restspotfinder.apicount.domain.PlaceSearchCount;
import com.restspotfinder.apicount.domain.RouteSearchCount;
import com.restspotfinder.apicount.exception.ApiCountErrorCode;
import com.restspotfinder.apicount.repository.AddressSearchCountRepository;
import com.restspotfinder.apicount.repository.PlaceSearchCountRepository;
import com.restspotfinder.apicount.repository.RouteSearchCountRepository;
import com.restspotfinder.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ApiCountService {
    private final PlaceSearchCountRepository placeSearchCountRepository;
    private final RouteSearchCountRepository routeSearchCountRepository;
    private final AddressSearchCountRepository addressSearchCountRepository;

    @Transactional
    public void checkPlaceSearchCount(LocalDate today) {
        PlaceSearchCount placeSearchCount = placeSearchCountRepository.findByDateWithPessimisticLock(today)
                .orElse(PlaceSearchCount.init(LocalDate.now()));

        long apiCallCount = placeSearchCount.getNaverCount();
        if (apiCallCount >= 25000) // 일일 한도 25,000 건
            throw new CommonException(ApiCountErrorCode.PLACE_API_CALL_LIMIT_EXCEEDED);

        placeSearchCount.increase();
        placeSearchCountRepository.save(placeSearchCount);
    }

    @Transactional
    public void checkRoutSearchCount(LocalDate today) {
        LocalDate startOfMonth = today.withDayOfMonth(1);
        RouteSearchCount routeSearchCount = routeSearchCountRepository.findByDateWithPessimisticLock(startOfMonth)
                .orElse(RouteSearchCount.init(startOfMonth));

        long apiCallCount = routeSearchCount.getNaverCount();
        if (apiCallCount >= 60000) // 월간 한도 60,000 건
            throw new CommonException(ApiCountErrorCode.ROUTE_API_CALL_LIMIT_EXCEEDED);

        routeSearchCount.increase();
        routeSearchCountRepository.save(routeSearchCount);
    }

    @Transactional
    public void checkAddressSearchCount(LocalDate today) {
        AddressSearchCount addressSearchCount = addressSearchCountRepository.findByCreatedAt(today)
                .orElse(AddressSearchCount.init(LocalDate.now()));

        long apiCallCount = addressSearchCount.getNaverCount();
        if (apiCallCount >= 25000) // 일일 한도 25,000 건
            throw new CommonException(ApiCountErrorCode.PLACE_API_CALL_LIMIT_EXCEEDED);

        addressSearchCount.increase();
        addressSearchCountRepository.save(addressSearchCount);
    }
}