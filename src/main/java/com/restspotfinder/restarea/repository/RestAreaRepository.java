package com.restspotfinder.restarea.repository;

import com.restspotfinder.restarea.domain.RestArea;
import org.locationtech.jts.geom.LineString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestAreaRepository extends JpaRepository<RestArea, Long> {
    List<RestArea> findByRouteName(String routeName);

    /***
     * 경로 LineString <-> 휴게소 좌표 비교 (500m 이내에 있는지 체크)
     * 출발지(LineString 의 첫번째 값)와 가까운 순으로 정렬
     ***/
    @Query(value = "SELECT r.* FROM rest_area r " +
            "WHERE ST_DWithin( " +
            "    CAST(ST_SetSRID(:route, 4326) AS geography), " +
            "    CAST(ST_SetSRID(r.point, 4326) AS geography), :distance) " +
            "ORDER BY ST_Distance(ST_SetSRID(r.point, 4326), ST_StartPoint(:route)) ASC",
            nativeQuery = true)
    List<RestArea> findNearbyRoutes(@Param("route") LineString route, @Param("distance") int distance);

}