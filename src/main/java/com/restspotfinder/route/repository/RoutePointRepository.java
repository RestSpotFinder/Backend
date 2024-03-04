package com.restspotfinder.route.repository;

import com.restspotfinder.route.domain.RoutePoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutePointRepository extends JpaRepository<RoutePoint, Long> {
}