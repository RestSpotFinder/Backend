package com.restspotfinder.common;

import org.springframework.http.ResponseEntity;

public class CommonController {
    public ResponseEntity<?> SuccessReturn() {
        return ResponseEntity.ok().body(CommonResponse.from(ResponseCode.SUCCESS));
    }

    public ResponseEntity<?> SuccessReturn(Object data) {
        return ResponseEntity.ok().body(CommonResponse.from(ResponseCode.SUCCESS, data));
    }

    public ResponseEntity<?> ErrorReturn(ResponseCode responseCode) {
        return ResponseEntity.ok().body(CommonResponse.from(responseCode));
    }

    public ResponseEntity<?> ErrorReturn(ResponseCode responseCode, Object data) {
        return ResponseEntity.ok().body(CommonResponse.from(responseCode, data));
    }
}
