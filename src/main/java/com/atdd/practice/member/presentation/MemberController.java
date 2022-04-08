package com.atdd.practice.member.presentation;

import com.atdd.practice.common.apiresponse.ApiResponse;
import com.atdd.practice.common.apiresponse.ApiResponseUtils;
import com.atdd.practice.member.application.MemberLoginInfo;
import com.atdd.practice.member.application.MemberService;
import com.atdd.practice.member.presentation.dto.request.MemberJoinRequest;
import com.atdd.practice.member.presentation.dto.response.EmailDuplicateResultMessage;
import com.atdd.practice.member.presentation.dto.response.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/api/v1/members")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResponse> join(@RequestBody MemberJoinRequest memberJoinRequest) {
        MemberJoinResponse memberJoinResponse = memberService.join(memberJoinRequest);
        return ResponseEntity.ok()
                .body(ApiResponseUtils.createSuccessResponse(memberJoinResponse, "회원가입에 성공했습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> checkOfDuplicateEmail(
            @RequestParam(value = "email", required = false) String requestEmail) {
        EmailDuplicateResultMessage resultMessage = memberService.checkOfDuplicateEmail(requestEmail);
        return ResponseEntity.ok()
                .body(ApiResponseUtils.createSuccessResponse(resultMessage.getMessage()));
    }

}
