package com.restspotfinder.restarea.controller;

import com.restspotfinder.common.CommonController;
import com.restspotfinder.restarea.domain.RestArea;
import com.restspotfinder.restarea.response.RestAreaResponse;
import com.restspotfinder.restarea.service.RestAreaService;
import com.restspotfinder.route.domain.Route;
import com.restspotfinder.route.service.RouteService;
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


@Tag(name = "휴게소[RestArea] API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restarea")
public class RestAreaController extends CommonController {
    private final RestAreaService restAreaService;
    private final RouteService routeService;

    @Operation(summary = "단 건 휴게소 조회 API")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array =
    @ArraySchema(schema = @Schema(implementation = RestAreaResponse.class)))})
    @GetMapping
    public ResponseEntity<?> getOneByRestAreaId(@RequestParam long restAreaId) {
        RestArea restArea = restAreaService.getOneById(restAreaId);

        return SuccessReturn(RestAreaResponse.from(restArea));
    }

    @Operation(summary = "경로 별 접근 가능 휴게소 목록 조회 API")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array =
    @ArraySchema(schema = @Schema(implementation = RestAreaResponse.class)))})
    @GetMapping("/route")
    public ResponseEntity<?> getListByRouteId(@RequestParam long routeId) {
        Route route = routeService.getOneById(routeId);
        List<RestArea> restAreaList = restAreaService.getListNearbyRoutes(route);
        List<RestArea> filteredRestAreaList = restAreaService.filterAccessibleRestAreas(route, restAreaList);

        return SuccessReturn(RestAreaResponse.from(filteredRestAreaList));
    }
}