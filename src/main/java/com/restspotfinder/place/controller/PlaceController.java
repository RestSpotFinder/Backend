package com.restspotfinder.place.controller;

import com.restspotfinder.common.CommonController;
import com.restspotfinder.place.domain.NaverPlace;
import com.restspotfinder.place.response.PlaceResponse;
import com.restspotfinder.place.service.NaverPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "장소[Place] API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place")
public class PlaceController extends CommonController {
    private final NaverPlaceService naverPlaceSearchService;

    @Operation(summary = "NAVER 장소 검색 API")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PlaceResponse.class)))})
    @GetMapping("/naver")
    public ResponseEntity<?> getPlacesBySearchTerm(@RequestParam String searchTerm) {
        List<NaverPlace> naverPlaceList = naverPlaceSearchService.getPlaceListBySearchTerm(searchTerm);
        return SuccessReturn(PlaceResponse.fromList(naverPlaceList));
    }
}