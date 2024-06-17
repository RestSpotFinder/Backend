package com.restspotfinder.search.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long searchId;
    @Enumerated(EnumType.STRING)
    private SearchType type; // ex) recent, route
    private LocalDateTime createdAt;

    public static Search from(SearchType searchType){
        return Search.builder()
                .type(searchType)
                .createdAt(LocalDateTime.now())
                .build();
    }
}