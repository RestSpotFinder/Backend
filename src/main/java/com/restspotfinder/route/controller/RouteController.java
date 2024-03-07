package com.restspotfinder.route.controller;

import com.restspotfinder.common.CommonController;
import com.restspotfinder.route.domain.NaverRoute;
import com.restspotfinder.route.domain.Route;
import com.restspotfinder.route.response.RouteResponse;
import com.restspotfinder.route.service.NaverRouteService;
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

@Tag(name = "경로[Route] API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/route")
public class RouteController extends CommonController {
    private final RouteService routeService;
    private final NaverRouteService naverRouteService;

    @Operation(summary = "경로 조회 API", description = "경유지[waypoints] 는 최대 5개 구분자는 <b>%7c</b> 를 사용한다. " +
            "<br> <b>Ex) 127.1464289,36.8102415%7C127.3923500,36.6470900 </b> " +
            "<br> <br>page 는 1 or 2를 사용 한다. " +
            "<br> <b>Ex) 1일 경우 [fast, optimal, comfort] 2일 경우 [avoidtoll, avoidcaronly] 타입을 반환 한다.</b>")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array =
    @ArraySchema(schema = @Schema(implementation = RouteResponse.class)))})
    @GetMapping
    public ResponseEntity<?> getRouteByPoint(@RequestParam String start,
                                             @RequestParam String goal,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(required = false) String waypoints) {
        List<NaverRoute> naverRouteList = naverRouteService.getRouteData(start, goal, waypoints, page);
        List<Route> routeList = routeService.create(naverRouteList);

        return SuccessReturn(RouteResponse.fromList(routeList));
    }
}