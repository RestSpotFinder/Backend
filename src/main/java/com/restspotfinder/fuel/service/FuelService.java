package com.restspotfinder.fuel.service;

import com.restspotfinder.fuel.domain.FuelStation;
import com.restspotfinder.fuel.repository.FuelStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    public FuelStation findByServiceAreaName(String name) {
        return fuelStationRepository.findByServiceAreaName(name)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        String.format("'%s' 이름의 주유소를 찾을 수 없습니다.", name)
                ));
    }

    public List<FuelStation> getAllStations() {
        return fuelStationRepository.findAll();
    }
}
