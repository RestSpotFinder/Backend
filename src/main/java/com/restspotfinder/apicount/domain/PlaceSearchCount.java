package com.restspotfinder.apicount.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceSearchCount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long countId;
    private int naverCount;
    private LocalDate createdAt;

    public static PlaceSearchCount init(LocalDate date){
        return PlaceSearchCount.builder()
                .naverCount(0)
                .createdAt(date)
                .build();
    }

    public int increase(){
        return ++naverCount;
    }
}