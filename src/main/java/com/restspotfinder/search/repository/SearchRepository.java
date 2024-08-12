package com.restspotfinder.search.repository;

import com.restspotfinder.search.domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SearchRepository extends JpaRepository<Search, Long> {
}