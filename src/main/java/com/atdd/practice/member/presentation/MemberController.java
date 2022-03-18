package com.atdd.practice.member.presentation;

import com.atdd.practice.common.apiresponse.ApiResponse;
import com.atdd.practice.common.apiresponse.ApiResponseUtils;
import com.atdd.practice.member.application.MemberService;
import com.atdd.practice.member.presentation.dto.request.MemberJoinRequest;
import com.atdd.practice.member.presentation.dto.response.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
