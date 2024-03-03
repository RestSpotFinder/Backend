package com.restspotfinder.route.controller;

import com.restspotfinder.common.CommonController;
import com.restspotfinder.route.domain.RouteOption;
import com.restspotfinder.route.response.RouteResponse;
import com.restspotfinder.route.service.NaverGeoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "경로[Route] API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/route")
public class RouteController extends CommonController {
    private final NaverGeoService naverGeoService;

    // 경로 조회 API
    @Operation(summary = "경로 조회 API")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RouteResponse.class)))})
    @GetMapping
    public ResponseEntity<?> getRoute(@RequestParam String start, @RequestParam String goal) {
        // NAVER 에서 경로 데이터 조회
        Map<RouteOption, Coordinate[]> naverRouteData = naverGeoService.getRouteData(start, goal);
        List<RouteResponse> responseList = naverRouteData.entrySet().stream()
                .map(entry -> RouteResponse.from(entry.getValue(), entry.getKey()))
                .toList();

        return SuccessReturn(responseList);
    }
}