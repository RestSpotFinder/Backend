package com.restspotfinder.apicount.repository;

import com.restspotfinder.apicount.domain.PlaceSearchCount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface PlaceSearchCountRepository extends JpaRepository<PlaceSearchCount, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM PlaceSearchCount c WHERE c.createdAt = :today")
    PlaceSearchCount findByDateWithPessimisticLock(LocalDate today);
}