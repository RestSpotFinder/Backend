package com.restspotfinder.route.domain;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;
    private Long searchId;
    private String distance; // 거리 (meters)
    private String duration; // 시간 (millisecond)
    private String tollFare; // 통행료
    private String fuelPrice; // 연료비
    @Enumerated(EnumType.STRING)
    private RouteOption routeOption;  // 경로 옵션
    @Column(columnDefinition = "geometry(LineString, 4326)")
    private LineString lineString; // 경로 Path
    private LocalDateTime createdDate;

    public static Route from(NaverRoute naverRoute, long searchId) {
        return Route.builder()
                .searchId(searchId)
                .distance(naverRoute.getDistance())
                .duration(naverRoute.getDuration())
                .tollFare(naverRoute.getTollFare())
                .fuelPrice(naverRoute.getFuelPrice())
                .lineString(new GeometryFactory().createLineString(naverRoute.getPath()))
                .routeOption(naverRoute.getOption())
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static List<Route> fromList(List<NaverRoute> naverRouteList, long searchId) {
        return naverRouteList.stream().map(naverRoute -> Route.from(naverRoute, searchId)).toList();
    }
}