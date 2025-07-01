package com.restspotfinder.domain.fuel.error;

import com.restspotfinder.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FuelErrorCode implements ErrorCode {
    NOT_FOUND("FUEL_NOT_FOUND", "해당 주유소를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
} 