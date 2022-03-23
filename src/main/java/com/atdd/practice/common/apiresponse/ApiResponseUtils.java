package com.atdd.practice.common.apiresponse;

import com.atdd.practice.common.exception.NotInstanceException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ApiResponseUtils {

    public ApiResponseUtils() {
        throw new NotInstanceException();
    }

    public static ApiResponse createSuccessResponse() {
        return new ApiSuccessResponse<>(null);
    }

    public static ApiResponse createSuccessResponse(String message) {
        return new ApiSuccessResponse<>(message);
    }

    public static <T> ApiResponse createSuccessResponse(T data, String message) {
        return new ApiSuccessResponse<>(data, message);
    }

    public static ApiResponse createFailResponse(Exception e) {
        return new ApiFailResponse<>(
                Collections.singletonList(new DefaultErrorTemplate(e.getMessage(), e.getClass().getTypeName())));
    }

    public static ApiResponse createFailResponse(String message) {
        return new ApiFailResponse<>(
                Collections.singletonList(new DefaultErrorTemplate(message)));
    }

    public static ApiResponse createFailResponse(MethodArgumentNotValidException methodArgumentNotValidException) {
        return new ApiFailResponse<>(convertErrorTemplateList(methodArgumentNotValidException));
    }

    private static List<DefaultErrorTemplate> convertErrorTemplateList(MethodArgumentNotValidException e) {
        return e.getBindingResult().getAllErrors().stream()
                .map(error -> new DefaultErrorTemplate(error.getDefaultMessage(), e.getClass().getTypeName()))
                .collect(Collectors.toList());
    }
}
