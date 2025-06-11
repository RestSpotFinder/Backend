package com.restspotfinder.fuel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class FuelStation {

    @Id
    private String serviceAreaCode; // 영업부대시설코드

    private String serviceAreaName; // 주유소명
    private String routeName; // 노선명
    private String direction; // 방향
    private String gasolinePrice; // 휘발유가격
    private String diselPrice; // 경유가격
    private String lpgPrice; // LPG가격
    private String oilCompany;
    private String telNo; // 전화번호
    private String svarAddr; // 휴게소주소

    public FuelStation(String serviceAreaCode, String serviceAreaName, String routeName, String direction,
                       String gasolinePrice, String diselPrice, String lpgPrice,
                       String oilCompany, String telNo, String svarAddr) {
        this.serviceAreaCode = serviceAreaCode;
        this.serviceAreaName = serviceAreaName;
        this.routeName = routeName;
        this.direction = direction;
        this.gasolinePrice = gasolinePrice;
        this.diselPrice = diselPrice;
        this.lpgPrice = lpgPrice;
        this.oilCompany = oilCompany;
        this.telNo = telNo;
        this.svarAddr = svarAddr;
    }
}
