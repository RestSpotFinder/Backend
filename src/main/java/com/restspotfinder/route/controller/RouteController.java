package com.restspotfinder.route.controller;

import com.restspotfinder.apicount.service.ApiCountService;
import com.restspotfinder.common.CommonController;
import com.restspotfinder.route.controller.request.RouteRequestDTO;
import com.restspotfinder.route.domain.NaverRoute;
import com.restspotfinder.route.domain.Route;
import com.restspotfinder.route.response.RouteResponse;
import com.restspotfinder.route.service.NaverRouteService;
import com.restspotfinder.route.service.RouteService;
import com.restspotfinder.search.domain.Search;
import com.restspotfinder.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "경로[Route] API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/route")
public class RouteController extends CommonController {
    private final RouteService routeService;
    private final ApiCountService apiCountService;
    private final SearchService searchService;
    private final NaverRouteService naverRouteService;

    @Operation(summary = "경로 검색 API", description = "경유지(waypoints)는 최대 5개까지 이며, 구분자로 %7c를 사용 한다. " +
            "<br> <b>Ex) 127.1464289,36.8102415%7C127.3923500,36.6470900 </b> " +
            "<br> <br> page 는 1 or 2를 사용 한다. " +
            "<br> <b>Ex) 1일 경우 [fast, optimal, comfort] 2일 경우 [avoidtoll, avoidcaronly] 타입을 반환 한다.</b>" +
            "<br> <br> 월간 API 호출 제한량은 60,000 건이다. <b>60,000 건 초과 시 303 에러(API_CALL_LIMIT_ERROR)가 발생 한다.</b>")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array =
    @ArraySchema(schema = @Schema(implementation = RouteResponse.class)))})
    @GetMapping
    public ResponseEntity<?> getRouteByPoint(@ModelAttribute RouteRequestDTO routeRequestDTO) {
        // 월간 한도 60,000 건
        apiCountService.checkRoutSearchCount(LocalDate.now());

        List<NaverRoute> naverRouteList = naverRouteService.getRouteData(routeRequestDTO);
        Search search = searchService.createByRoute(routeRequestDTO);
        List<Route> routeList = routeService.create(naverRouteList, search.getSearchId(), routeRequestDTO);

        return SuccessReturn(RouteResponse.fromList(routeList));
    }

    @Operation(summary = "SearchId로 경로 검색 API")
    @GetMapping("/search")
    public ResponseEntity<?> getRouteById(@RequestParam long searchId) {
        List<Route> routeList = routeService.getListBySearchId(searchId);
        searchService.createByRecent();

        return SuccessReturn(RouteResponse.fromList(routeList));
    }
}