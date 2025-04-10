package com.restspotfinder.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/test")
public class ExceptionTestController {
    @GetMapping("/error")
    public ResponseEntity<?> getError() {
        log.error("Simulating an error in GET request");
        throw new CommonException(ExceptionTestErrorCode.GET_TEST_ERROR);
    }

    @PostMapping("/error")
    public ResponseEntity<?> postError() {
        log.error("Simulating an error in POST request");
        throw new CommonException(ExceptionTestErrorCode.POST_TEST_ERROR);
    }
}
