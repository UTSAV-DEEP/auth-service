package com.utsav.authservice.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
        return toResponseEntity(new HttpErrorException("Something went wrong", e));
    }

    @ExceptionHandler({HttpErrorException.class})
    public ResponseEntity<String> handleHttpErrorException(HttpErrorException e) {
        return toResponseEntity(e);
    }

    private ResponseEntity<String> toResponseEntity(HttpErrorException e) {
        LOG.error("Exception : ", e);
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}