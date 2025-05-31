package com.restspotfinder.sleeparea.repository;

import com.restspotfinder.sleeparea.domain.SleepArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SleepAreaRepository extends JpaRepository<SleepArea, Long> {
}
