package com.restspotfinder.survey.repository;

import com.restspotfinder.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}