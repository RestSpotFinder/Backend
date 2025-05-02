package com.restspotfinder.restarea.service;

import com.restspotfinder.restarea.domain.RestArea;
import com.restspotfinder.route.domain.Route;
import com.restspotfinder.route.service.RouteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class RestAreaServiceTest {
    @Autowired
    RouteService routeService;

    @Autowired
    RestAreaService restAresService;

    @Test
    void filterAccessibleRestAreas() {
        long routeId = 590;
        Route route = routeService.getOneById(routeId);

        List<RestArea> filteredList = restAresService.getAccessibleRestAreas(route).restAreaList();

        for (RestArea restArea : filteredList) {
            System.out.println("restArea = " + restArea);
        }
        // then
    }
}