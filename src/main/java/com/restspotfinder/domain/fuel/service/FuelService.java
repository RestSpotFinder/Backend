package com.restspotfinder.domain.fuel.service;

import com.restspotfinder.domain.fuel.entity.FuelStation;
import com.restspotfinder.domain.fuel.repository.FuelStationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuelService {
    private final FuelStationRepository fuelStationRepository;
    private final FuelApiClient fuelApiClient;

    public List<FuelStation> getAll() {
        return fuelStationRepository.findAll();
    }

    @Transactional
    public void updateFuelStationsFromApi() {
        List<FuelStation> fetchedList = fuelApiClient.fetchAll();
        fuelStationRepository.saveAll(fetchedList);
    }
}
