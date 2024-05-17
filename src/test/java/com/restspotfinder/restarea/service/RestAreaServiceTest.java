package com.restspotfinder.restarea.service;

import com.restspotfinder.place.domain.NaverPlace;
import com.restspotfinder.place.service.NaverPlaceService;
import com.restspotfinder.restarea.domain.RestArea;
import com.restspotfinder.restarea.repository.RestAreaRepository;
import com.restspotfinder.route.repository.RouteRepository;
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
    RestAreaService restAresService;
    @Autowired
    RestAreaRepository restAreaRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    NaverPlaceService naverPlaceService;

    @Test
    void filterAccessibleRestAreas() {
        long routeId = 590;

        List<RestArea> restAreaList = restAresService.getListNearbyRoutes(routeId);
        for (RestArea restArea : restAreaList) {
            System.out.println("restArea = " + restArea);
        }

        List<RestArea> filteredList = restAresService.filterAccessibleRestAreas(routeId, restAreaList);

        for (RestArea restArea : filteredList) {
            System.out.println("restArea = " + restArea);
        }
        // then
    }
}