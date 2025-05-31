package com.restspotfinder.apicount.repository;

import com.restspotfinder.apicount.domain.AddressSearchCount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDate;
import java.util.Optional;

public interface AddressSearchCountRepository extends JpaRepository<AddressSearchCount, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<AddressSearchCount> findByCreatedAt(LocalDate today);
}


