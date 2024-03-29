package com.restspotfinder.apicount.repository;

import com.restspotfinder.apicount.domain.RouteSearchCount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface RouteSearchCountRepository extends JpaRepository<RouteSearchCount, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM RouteSearchCount c WHERE c.createdAt = :startOfMonth")
    RouteSearchCount findByDateWithPessimisticLock(LocalDate startOfMonth);
}