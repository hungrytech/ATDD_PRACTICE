package com.atdd.practice.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorizeException extends RuntimeException {

    private final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public AuthorizeException(String message) {
        super(message);
    }
}
