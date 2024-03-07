package com.restspotfinder.restarea.response;

import com.restspotfinder.restarea.domain.RestArea;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;


@Getter
@Builder
public class RestAreaResponse {
    @Schema(description = "휴게소 고유 ID")
    private Long restAreaId;
    @Schema(description = "휴게소 명")
    private String name;
    @Schema(description = "도로 명")
    private String routeName;
    @Schema(description = "방향", example = "상행, 하행, 양방향")
    private String routeDirection;
    @Schema(description = "위도")
    private double latitude;
    @Schema(description = "경도")
    private double longitude;
    @Schema(description = "휴게소 종류")
    private String type;
    @Schema(description = "휴게소 운영 시작 시각")
    private String operatingStartTime;
    @Schema(description = "휴게소 운영 종료 시각")
    private String operatingEndTime;
    @Schema(description = "주차면 수")
    private int parkingSpaceCount;
    @Schema(description = "경정비 가능 여부")
    private Boolean isMaintenanceAvailable;
    @Schema(description = "주유소 유무")
    private Boolean hasGasStation;
    @Schema(description = "LPG 충전소 유무")
    private Boolean hasLpgChargingStation;
    @Schema(description = "전기차 충전소 유무")
    private Boolean hasElectricChargingStation;
    @Schema(description = "화장실 유무")
    private Boolean hasRestroom;
    @Schema(description = "약국 유무")
    private Boolean hasPharmacy;
    @Schema(description = "수유실 유무")
    private Boolean hasNursingRoom;
    @Schema(description = "매점 유무")
    private Boolean hasStore;
    @Schema(description = "음식점 유무")
    private Boolean hasRestaurant;
    @Schema(description = "기타 편의 시설")
    private String otherFacilities;
    @Schema(description = "휴게소 대표 음식명")
    private String representativeFood;
    @Schema(description = "휴게소 전화 번호")
    private String phoneNumber;

    public static RestAreaResponse from(RestArea restArea) {
        return RestAreaResponse.builder()
                .restAreaId(restArea.getRestAreaId())
                .name(restArea.getName())
                .routeName(restArea.getRouteName())
                .routeDirection(restArea.getRouteDirection())
                .latitude(restArea.getLatitude())
                .longitude(restArea.getLongitude())
                .type(restArea.getType())
                .operatingStartTime(restArea.getOperatingStartTime())
                .operatingEndTime(restArea.getOperatingEndTime())
                .parkingSpaceCount(restArea.getParkingSpaceCount())
                .isMaintenanceAvailable(restArea.getIsMaintenanceAvailable())
                .hasGasStation(restArea.getHasGasStation())
                .hasLpgChargingStation(restArea.getHasLpgChargingStation())
                .hasElectricChargingStation(restArea.getHasElectricChargingStation())
                .hasRestroom(restArea.getHasRestroom())
                .hasPharmacy(restArea.getHasPharmacy())
                .hasNursingRoom(restArea.getHasNursingRoom())
                .hasStore(restArea.getHasStore())
                .hasRestaurant(restArea.getHasRestaurant())
                .otherFacilities(restArea.getOtherFacilities())
                .representativeFood(restArea.getRepresentativeFood())
                .phoneNumber(restArea.getPhoneNumber())
                .build();
    }

    public static List<RestAreaResponse> fromList(List<RestArea> restAreaList){
        return restAreaList.stream().map(RestAreaResponse::from).toList();
    }
}