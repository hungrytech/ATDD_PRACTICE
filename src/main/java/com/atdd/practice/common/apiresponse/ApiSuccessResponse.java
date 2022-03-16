package com.atdd.practice.common.apiresponse;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class ApiSuccessResponse<T> extends ApiResponse {

    private final T data;

    private final String message;

    public ApiSuccessResponse(T data, String message) {
        super(true);
        this.data = data;
        this.message = message;
    }

    public ApiSuccessResponse(String message) {
        super(true);
        this.data = null;
        this.message = message;
    }
}
