package com.restspotfinder.restarea.service;

import com.restspotfinder.restarea.domain.RestArea;
import com.restspotfinder.restarea.repository.RestAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestAreaService {
    private final RestAreaRepository restAreaRepository;

    public List<RestArea> getListNearbyRoutes(long routeId) {
        return restAreaRepository.findNearbyRoutes(routeId);
    }

    public RestArea getOneById(long restAreaId) {
        return restAreaRepository.findById(restAreaId)
                .orElseThrow(() -> new NullPointerException("[RestArea] restAreaId : " + restAreaId));
    }

    public List<RestArea> filterAccessibleRestAreas(List<RestArea> initList){
        Map<String, List<RestArea>> groupingMap = RestArea.listToGroupingMap(initList);
        Map<String, String> directionMap = groupingMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> getRouteDirection(entry.getValue())));

        return initList.stream()
                .filter(r -> {
                    String direction = directionMap.get(r.getRouteName());
                    return direction != null && (direction.equals("판별 불가") || r.getRouteDirection().equals(direction) || r.getRouteDirection().equals("양방향"));
                }).toList();
    }

    /***
     * 도로명 기준 으로 필터링 (경부선, 중부선)
     * 각 도로 기준 으로 방향 판단
     * 판단된 방향에 존재 하는 RestArea 만 필터링
    */
    public String getRouteDirection(List<RestArea> restAreaList) {
        if (restAreaList.size() < 2) // 휴게소 가 1개 이하인 경우 판단할 수 없다.
            return "판별 불가";

        RestArea firstArea = restAreaList.get(0);
        RestArea secondArea = restAreaList.get(1);

        int weight = firstArea.getWeight() - secondArea.getWeight();
        return (weight > 0) ? "상행" : "하행";
    }
}