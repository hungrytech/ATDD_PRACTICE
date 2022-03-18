package com.atdd.practice.member.presentation;

import com.atdd.practice.common.apiresponse.ApiResponse;
import com.atdd.practice.common.apiresponse.ApiResponseUtils;
import com.atdd.practice.member.application.AuthService;
import com.atdd.practice.member.presentation.dto.request.MemberLoginRequest;
import com.atdd.practice.member.presentation.dto.response.MemberLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody MemberLoginRequest memberLoginRequest) {

        MemberLoginResponse memberLoginResponse = authService.login(memberLoginRequest);
        return ResponseEntity.ok()
                .body(ApiResponseUtils.createSuccessResponse(memberLoginResponse, "로그인에 성공했습니다."));
    }
}
