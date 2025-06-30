package com.restspotfinder.domain.fuel.controller;

import com.restspotfinder.domain.fuel.entity.FuelStation;
import com.restspotfinder.domain.fuel.service.FuelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fuel")
@RequiredArgsConstructor
public class FuelController {

    private final FuelService fuelService;

    @PutMapping("/update")
    public String updateFuelStations() {
        fuelService.updateFuelStationsFromApi();
        return "✅ 주유소 데이터가 DB에 저장되었습니다.";
    }

    @GetMapping("/all")
    public List<FuelStation> getAll() {
        return fuelService.getAllStations();
    }
}
