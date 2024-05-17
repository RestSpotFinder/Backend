package com.restspotfinder.interchange.repository;

import com.restspotfinder.interchange.domain.Interchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface InterchangeRepository extends JpaRepository<Interchange, Long> {
    List<Interchange> findByNameContainingOrderByInterchangeId(String name);

    @Query(value = "SELECT i.* FROM interchange i " +
            "JOIN ( " +
            "    SELECT rt.line_string " +
            "    FROM route rt " +
            "    WHERE rt.route_id = :routeId" +
            ") filtered_route ON ST_DWithin( " +
            "    CAST(ST_SetSRID(filtered_route.line_string, 4326) AS geography), " +
            "    CAST(ST_SetSRID(i.point, 4326) AS geography), " +
            "    300) " +
            "WHERE i.route_name = :routeName " +
            "ORDER BY ST_Distance( " +
            "    CAST(ST_SetSRID(i.point, 4326) AS geography), " +
            "    CAST(ST_SetSRID(ST_StartPoint(filtered_route.line_string), 4326) AS geography)) ASC",
            nativeQuery = true)
    List<Interchange> findNearbyRoutes(@Param("routeId") long routeId, @Param("routeName") String routeName);

    @Query(value = "SELECT i.* FROM interchange i " +
            "WHERE i.route_name = :routeName " +
            "ORDER BY ST_Distance( " +
            "    CAST(ST_SetSRID(i.point, 4326) AS geography), " +
            "    (SELECT CAST(ST_SetSRID(point, 4326) AS geography) " +
            "     FROM interchange " +
            "     WHERE route_name = :routeName AND is_start = true LIMIT 1)) ASC",
            nativeQuery = true)
    List<Interchange> sortHighwayByStartPoint(@Param("routeName") String routeName);
}