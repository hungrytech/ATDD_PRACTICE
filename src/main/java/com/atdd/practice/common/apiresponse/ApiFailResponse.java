package com.atdd.practice.common.apiresponse;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ApiFailResponse<T> extends ApiResponse {

    private final List<T> error;

    public ApiFailResponse(List<T> error) {
        super(false);
        this.error = (error == null || error.isEmpty()) ? Collections.emptyList() : error;
    }
}
