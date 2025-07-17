package com.restspotfinder.domain.fuel.repository;

import com.restspotfinder.domain.fuel.entity.FuelUpdateHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FuelUpdateHistoryRepository extends JpaRepository<FuelUpdateHistory, Long> {
}
