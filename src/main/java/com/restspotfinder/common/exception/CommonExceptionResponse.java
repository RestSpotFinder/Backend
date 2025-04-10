package com.restspotfinder.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.slf4j.MDC;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommonExceptionResponse {
    private String code;
    private String message;
    private String traceId; // log_module (https://github.com/Gupuroom/log_module.git) 연동
    private LocalDateTime timestamp;

    public static CommonExceptionResponse of(CommonErrorCode errorCode) {
        String traceId = MDC.get("traceId"); // log_module (https://github.com/Gupuroom/log_module.git) 연동
        return CommonExceptionResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .traceId(traceId)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
