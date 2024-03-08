package com.restspotfinder.search.repository;

import com.restspotfinder.search.domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SearchRepository extends JpaRepository<Search, Long> {
    @Query("SELECT COUNT(s) FROM Search s WHERE DATE(s.createdAt) = :specificDate AND s.type = 'place'")
    int countForPlace(LocalDate specificDate);
}