package com.atdd.practice.common.apiresponse;

import lombok.Getter;

@Getter
public class DefaultErrorTemplate extends ErrorTemplate {

    private final String exception;

    public DefaultErrorTemplate(String message) {
        this(message, null);
    }

    public DefaultErrorTemplate(String message, String exception) {
        super(message);
        this.exception = exception;
    }
}
