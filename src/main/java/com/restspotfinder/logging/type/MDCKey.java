package com.restspotfinder.logging.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MDCKey {
    TRACE_ID("traceId");

    private final String key;
}