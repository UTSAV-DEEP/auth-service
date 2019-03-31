package com.utsav.authservice.exceptions;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class HttpErrorException extends RuntimeException {
    private HttpStatus httpStatus;

    public HttpErrorException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
