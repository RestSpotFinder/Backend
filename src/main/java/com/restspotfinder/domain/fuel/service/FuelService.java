package com.restspotfinder.domain.fuel.service;

import com.restspotfinder.domain.fuel.entity.FuelStation;
import com.restspotfinder.domain.fuel.repository.FuelStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;
import com.restspotfinder.exception.BusinessException;
import com.restspotfinder.domain.fuel.error.FuelErrorCode;

@Service
@RequiredArgsConstructor
public class FuelService {
    private final FuelStationRepository fuelStationRepository;
    private final FuelApiClient fuelApiClient;

    public void updateFuelStationsFromApi() {
        List<FuelStation> fetchedList = fuelApiClient.fetchAll();
        fuelStationRepository.saveAll(fetchedList);
    }

    public FuelStation findByServiceAreaName(String name) {
        return fuelStationRepository.findByServiceAreaName(name)
                .orElseThrow(() -> new BusinessException(FuelErrorCode.NOT_FOUND));
    }

    public List<FuelStation> getAllStations() {
        return fuelStationRepository.findAll();
    }
}
