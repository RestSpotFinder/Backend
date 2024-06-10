package com.restspotfinder.survey.service;

import com.restspotfinder.survey.controller.request.SurveyCreate;
import com.restspotfinder.survey.domain.Survey;
import com.restspotfinder.survey.repository.SurveyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    @Transactional
    public void create(SurveyCreate surveyCreate) {
        Survey survey = Survey.from(surveyCreate.text(), surveyCreate.email());
        surveyRepository.save(survey);
    }
}