package com.restspotfinder.apicount.service;

import com.restspotfinder.apicount.domain.PlaceSearchCount;
import com.restspotfinder.apicount.repository.PlaceSearchCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CountService {
    private final PlaceSearchCountRepository placeSearchCountRepository;

    @Transactional
    public int increasePlaceSearchCount(LocalDate today) {
        PlaceSearchCount count = placeSearchCountRepository.findByTodayWithPessimisticLock(today);
        int placeSearchCount = count.increase();

        placeSearchCountRepository.save(count);
        return placeSearchCount;
    }
}