package com.restspotfinder.apicount.exception;

import com.exception_module.exception.CommonErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiCountErrorCode implements CommonErrorCode {
    PLACE_API_CALL_LIMIT_EXCEEDED("1001", "Place API 호출 한도를 초과했습니다."),
    ROUTE_API_CALL_LIMIT_EXCEEDED("1002", "Route API 호출 한도를 초과했습니다.");

    private final String code;
    private final String message;
}