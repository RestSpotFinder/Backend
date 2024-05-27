package com.restspotfinder.interchange.service;

import com.restspotfinder.interchange.domain.Interchange;
import com.restspotfinder.interchange.repository.InterchangeRepository;
import com.restspotfinder.route.domain.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterchangeService {
    private final InterchangeRepository interchangeRepository;

    public List<Interchange> getListNearbyRoutes(Route route, String routeName) {
        return interchangeRepository.findNearbyRoutes(route.getLineString(), routeName, 300);
    }

    // TRUE: 하행, FALSE: 상행
    public String getDirectionByRoute(Route route, String routeName) {
        System.out.println("----------------------------------------------");
        System.out.println("routeName = " + routeName);
        // 경로 별 Interchange 조회 및 경로 시작점 기준 Sorting
        List<Interchange> interchangeList =getListNearbyRoutes(route, routeName);

        if (interchangeList.size() > 1) {
            Interchange start = interchangeList.get(0);
            Interchange end = interchangeList.get(interchangeList.size() - 1);

            int weight = start.getWeight() - end.getWeight();
            String direction =  (weight > 0) ? "상행" : "하행";

            System.out.println("direction = " + direction);
            return direction;
        } else {
            System.err.println("경로 판별 에러 남");
            return "판별 불가";
        }
    }
}