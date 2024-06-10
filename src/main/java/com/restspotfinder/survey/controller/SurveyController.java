package com.restspotfinder.survey.controller;

import com.restspotfinder.common.CommonController;
import com.restspotfinder.survey.controller.request.SurveyCreate;
import com.restspotfinder.survey.service.SurveyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "설문조사[Survey] API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/survey")
public class SurveyController extends CommonController {
    private final SurveyService searchService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SurveyCreate surveyCreate) {
        searchService.create(surveyCreate);

        return SuccessReturn();
    }
}