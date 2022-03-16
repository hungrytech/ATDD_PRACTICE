package com.atdd.practice.common.apiresponse;

import lombok.Getter;

@Getter
public class ErrorTemplate {

    private final String message;

    public ErrorTemplate(String message) {
        this.message = message;
    }
}
