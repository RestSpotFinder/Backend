package com.restspotfinder.fuel.service;

import com.restspotfinder.fuel.domain.FuelStation;
import com.restspotfinder.fuel.repository.FuelStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FuelService {
    private final FuelStationRepository fuelStationRepository;
    private final FuelApiClient fuelApiClient;

    public void updateFuelStationsFromApi() {
        List<FuelStation> fetchedList = fuelApiClient.fetchAll();
        fuelStationRepository.saveAll(fetchedList);
    }

    public Optional<FuelStation> findByServiceAreaName(String name) {
        return fuelStationRepository.findByServiceAreaName(name);
    }

    public Optional<FuelStation> findById(String serviceAreaCode) {
        return fuelStationRepository.findById(serviceAreaCode);
    }

    public List<FuelStation> getAllStations() {
        return fuelStationRepository.findAll();
    }
}
