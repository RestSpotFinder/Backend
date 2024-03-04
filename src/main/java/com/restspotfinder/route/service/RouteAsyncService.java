package com.restspotfinder.route.service;

import com.restspotfinder.route.domain.Route;
import com.restspotfinder.route.domain.RoutePoint;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteAsyncService {
    private final JdbcTemplate jdbcTemplate;

    @Async
    @Transactional
    public void insertBatchRoutePoints(List<Route> routeList) {
        for(Route route: routeList) {
            List<RoutePoint> routePoints = RoutePoint.fromList(route.getLineString().getCoordinates(), route.getRouteId());
            String sql = "INSERT INTO route_point (route_id, point, created_date) VALUES (?, ST_SetSRID(ST_MakePoint(?, ?), 4326), ?)";

            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    RoutePoint routePoint = routePoints.get(i);
                    Coordinate coordinate = routePoint.getPoint().getCoordinate();

                    ps.setLong(1, routePoint.getRouteId());
                    ps.setDouble(2, coordinate.x); // longitude
                    ps.setDouble(3, coordinate.y); // latitude
                    ps.setTimestamp(4, Timestamp.valueOf(routePoint.getCreatedDate()));
                }

                @Override
                public int getBatchSize() {
                    return routePoints.size();
                }
            });
        }
    }
}
