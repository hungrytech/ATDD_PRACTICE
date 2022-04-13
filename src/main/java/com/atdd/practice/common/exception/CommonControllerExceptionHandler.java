package com.atdd.practice.common.exception;

import com.atdd.practice.common.apiresponse.ApiResponse;
import com.atdd.practice.common.apiresponse.ApiResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonControllerExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse> handleInvalidRequestException(InvalidRequestException exception) {

        log.info("잘못된 요청 예외 발생 메세지: {} 원인: {}", exception.getMessage(), exception.getClass().getSimpleName());
        return ResponseEntity.status(exception.getHttpStatus())
                .body(ApiResponseUtils.createFailResponse(exception));
    }

    @ExceptionHandler(AuthorizeException.class)
    public ResponseEntity<ApiResponse> handleAuthorizeException(AuthorizeException exception) {
        log.info("등록되지 않은 리프레쉬 토큰 요청 메시지: {}", exception.getMessage());
        return ResponseEntity.status(exception.getHttpStatus())
                .body(ApiResponseUtils.createFailResponse(exception));
    }
}
