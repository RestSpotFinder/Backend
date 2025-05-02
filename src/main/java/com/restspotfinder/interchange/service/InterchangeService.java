package com.restspotfinder.interchange.service;

import com.restspotfinder.interchange.collection.Interchanges;
import com.restspotfinder.interchange.repository.InterchangeRepository;
import com.restspotfinder.route.type.Direction;
import com.restspotfinder.route.domain.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterchangeService {
    private final InterchangeRepository interchangeRepository;

    public Interchanges getInterchangesNearbyRoutes(Route route, String routeName) {
        return new Interchanges(interchangeRepository.findNearbyRoutes(route.getLineString(), routeName, 300));
    }


    // TRUE: 하행, FALSE: 상행
    public Direction getDirectionByRoute(Route route, String routeName) {
        System.out.println("----------------------------------------------");
        System.out.println("routeName = " + routeName);

        Interchanges interchanges = getInterchangesNearbyRoutes(route, routeName);
        Direction direction = interchanges.calculateDirection();
        System.out.println("direction = " + direction);
        return direction;
    }
}