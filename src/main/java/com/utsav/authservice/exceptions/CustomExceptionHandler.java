package com.utsav.authservice.exceptions;

import com.utsav.authservice.model.dtos.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRunTimeException(RuntimeException e) {
        return toResponseEntity(new HttpErrorException("Something went wrong", e));
    }

    @ExceptionHandler({HttpErrorException.class})
    public ResponseEntity<ErrorResponse> handleHttpErrorException(HttpErrorException e) {
        return toResponseEntity(e);
    }

    private ResponseEntity<ErrorResponse> toResponseEntity(HttpErrorException e) {
        LOG.error("Exception : ", e);
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getHttpStatus());
    }
}