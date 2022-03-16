package com.atdd.practice.common.apiresponse;

import lombok.Getter;

@Getter
public class ApiResponse {

    private final boolean success;

    public ApiResponse(boolean success) {
        this.success = success;
    }
}
