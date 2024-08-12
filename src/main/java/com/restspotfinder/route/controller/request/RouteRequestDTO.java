package com.restspotfinder.route.controller.request;

import com.restspotfinder.route.domain.RouteOption;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RouteRequestDTO {
    private String start;
    private String startName;
    private String goal;
    private String goalName;
    private int page = 1; // 기본값 설정
    private String waypoints;

    public String getDirect5RequestUrl(String direct5Url) {
        return direct5Url + "?start=" + start + "&goal=" + goal + "&waypoints=" + waypoints + "&option=" + RouteOption.getOptionValues(page);
    }

    public boolean isWaypointsEmpty(){
        return waypoints == null || waypoints.isBlank();
    }
}
