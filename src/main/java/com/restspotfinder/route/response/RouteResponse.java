package com.restspotfinder.route.response;

import com.restspotfinder.route.domain.RouteOption;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Coordinate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Builder
public class RouteResponse {
    @Schema(description = "경로 고유 ID")
    private Long routeId;
    @Schema(description = "경로 탐색 옵션", enumAsRef = true)
    private RouteOption routeOption;
    @Schema(description = "경로 조회 날짜")
    private LocalDateTime createdDate;
    @Schema(description = "경로 Path", example = "[[37.4321376, 127.128494],[37.4322478, 127.1288664]]")
    private List<Double[]> coordinates;

    public static RouteResponse from(Coordinate[] coordinates, RouteOption routeOption) {
        return RouteResponse.builder()
                .routeOption(routeOption)
                .createdDate(LocalDateTime.now())
                .coordinates(toArray(coordinates))
                .build();
    }

    // [37.4321376, 127.128494] 형식으로 변환
    public static List<Double[]> toArray(Coordinate[] coordinates) {
        return Arrays.stream(coordinates)
                .map(coordinate -> new Double[]{coordinate.getY(), coordinate.getX()})
                .toList();
    }
}