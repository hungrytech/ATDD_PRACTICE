package com.atdd.practice.common.apiresponse;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ApiFailResponse extends ApiResponse {

    private final String error;

    private final String errorDescription;

    public ApiFailResponse(boolean success, String error, String errorDescription) {
        super(success);
        this.error = error;
        this.errorDescription = errorDescription;
    }
}
