package com.restspotfinder.restarea.repository;

import com.restspotfinder.restarea.domain.RestArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestAreaRepository extends JpaRepository<RestArea, Long> {

    /***
     * 경로 LineString <-> 휴게소 좌표 비교 (500m 이내에 있는지 체크)
     * 출발지(LineString 의 첫번째 값)와 가까운 순으로 정렬
     ***/
//    @Query(value = "SELECT r.* FROM rest_area r " +
//            "JOIN route rt ON ST_DWithin( " +
//            "    CAST(ST_SetSRID(rt.line_string, 4326) AS geography), " +
//            "    CAST(ST_SetSRID(r.point, 4326) AS geography), " +
//            "    500) " +
//            "WHERE rt.route_id = :routeId " +
//            "ORDER BY ST_Distance( " +
//            "    CAST(ST_SetSRID(r.point, 4326) AS geography), " +
//            "    CAST(ST_SetSRID(ST_StartPoint(rt.line_string), 4326) AS geography)) ASC"
//            , nativeQuery = true)
//    List<RestArea> findNearbyRoutes(long routeId);

    @Query(value = "SELECT r.* FROM rest_area r " +
            "JOIN ( " +
            "    SELECT rt.line_string " +
            "    FROM route rt " +
            "    WHERE rt.route_id = :routeId " +
            ") filtered_route ON ST_DWithin( " +
            "    CAST(ST_SetSRID(filtered_route.line_string, 4326) AS geography), " +
            "    CAST(ST_SetSRID(r.point, 4326) AS geography), " +
            "    500) " +
            "ORDER BY ST_Distance( " +
            "    CAST(ST_SetSRID(r.point, 4326) AS geography), " +
            "    CAST(ST_SetSRID(ST_StartPoint(filtered_route.line_string), 4326) AS geography)) ASC",
            nativeQuery = true)
    List<RestArea> findNearbyRoutes(@Param("routeId") long routeId);
}