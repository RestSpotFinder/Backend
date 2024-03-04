package com.restspotfinder.route.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routePointId;
    private Long routeId;
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point point;
    private LocalDateTime createdDate;

    public static RoutePoint from(Coordinate coordinate, long routeId) {
        return RoutePoint.builder()
                .routeId(routeId)
                .point(new GeometryFactory().createPoint(coordinate))
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static List<RoutePoint> fromList(Coordinate[] coordinates, long routeId) {
        return Arrays.stream(coordinates).map(coordinate -> RoutePoint.from(coordinate, routeId)).toList();
    }
}
