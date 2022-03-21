package com.atdd.practice.member.presentation;

import com.atdd.practice.common.apiresponse.ApiResponse;
import com.atdd.practice.common.apiresponse.ApiResponseUtils;
import com.atdd.practice.member.application.AuthService;
import com.atdd.practice.member.presentation.dto.request.MemberLoginRequest;
import com.atdd.practice.member.presentation.dto.response.MemberLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import static java.time.LocalDateTime.now;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody MemberLoginRequest memberLoginRequest) {

        MemberLoginResponse memberLoginResponse = authService.login(memberLoginRequest);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, insertRefreshTokenInCookie(memberLoginResponse).toString())
                .body(ApiResponseUtils.createSuccessResponse(memberLoginResponse, "로그인에 성공했습니다."));
    }

    private ResponseCookie insertRefreshTokenInCookie(MemberLoginResponse memberLoginResponse) {
        return ResponseCookie.from("refreshToken", memberLoginResponse.getRefreshToken())
                .path("/")
                .maxAge(Duration.between(now(), memberLoginResponse.getRefreshTokenIn()))
                .httpOnly(true)
                .secure(true)
                .build();
    }
}
