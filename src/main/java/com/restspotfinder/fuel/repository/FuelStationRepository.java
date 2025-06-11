package com.restspotfinder.fuel.repository;

import com.restspotfinder.fuel.domain.FuelStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuelStationRepository extends JpaRepository<FuelStation, String> {
    Optional<FuelStation> findByServiceAreaName(String serviceAreaName);
}
