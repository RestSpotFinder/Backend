package com.restspotfinder.interchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;


@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Interchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interchangeId;
    private String name; // 출입구 명
    private String routeName; // 도로 노선 명
    private String routeCode; // 도로 노선 코드
    private double latitude; // 위도
    private double longitude; // 경도

    @JsonIgnore
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point point;

    public static Interchange from(JsonNode node) {
        GeometryFactory geometryFactory = new GeometryFactory();
        double xValue = node.get("경도").asDouble(0);
        double yValue = node.get("위도").asDouble(0);
        Point point = geometryFactory.createPoint(new Coordinate(xValue, yValue));
        return Interchange.builder()
                .name(node.get("IC명").asText("")) // 기본값으로 "" 사용
                .routeName(node.get("노선명").asText(""))
                .routeCode(node.get("노선코드").asText(""))
                .point(point)
                .latitude(yValue) // 기본값으로 0 사용
                .longitude(xValue)
                .build();
    }
}