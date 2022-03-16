package com.atdd.practice.common.security.entrypoint;

import com.atdd.practice.common.apiresponse.ApiResponseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationEntryPoint implements AuthenticationEntryPoint {

    private static final String AUTHORIZATION_FAIL_MESSAGE = "유효하지 않은 토큰입니다.";

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            objectMapper.writeValue(
                    response.getOutputStream(),
                    ApiResponseUtils.createFailResponse(AUTHORIZATION_FAIL_MESSAGE));
        } catch (IOException e) {
            log.trace("jwt 비인증 응답처리 실패. 이유 : {}", e.getMessage());
        }
    }
}
