package com.restspotfinder.fuel.controller;

import com.restspotfinder.fuel.domain.FuelStation;
import com.restspotfinder.fuel.service.FuelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fuel")
@RequiredArgsConstructor
public class FuelTestController {

    private final FuelService fuelService;

    @PostMapping("/update")
    public String updateFuelStations() {
        fuelService.updateFuelStationsFromApi();
        return "✅ 주유소 데이터가 DB에 저장되었습니다.";
    }

    @GetMapping("/all")
    public List<FuelStation> getAll() {
        return fuelService.getAllStations();
    }
}
