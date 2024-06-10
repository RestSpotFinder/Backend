package com.restspotfinder.survey.domain;

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
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long surveyId;
    private String text;
    private String email;
    private LocalDateTime createdAt;

    public static Survey from(String text, String email){
        return Survey.builder()
                .text(text)
                .email(email)
                .createdAt(LocalDateTime.now())
                .build();
    }
}