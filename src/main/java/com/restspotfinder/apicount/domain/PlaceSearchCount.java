package com.restspotfinder.apicount.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;


@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PlaceSearchCount {
    @Id
    private LocalDate createdAt;
    private int naverCount;


    public static PlaceSearchCount init(LocalDate date){
        return PlaceSearchCount.builder()
                .createdAt(date)
                .naverCount(0)
                .build();
    }

    public int increase(){
        return ++naverCount;
    }
}