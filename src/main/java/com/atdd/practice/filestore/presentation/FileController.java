package com.atdd.practice.filestore.presentation;

import com.atdd.practice.common.apiresponse.ApiResponse;
import com.atdd.practice.member.application.MemberLoginInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/api/v1")
@RestController
public class FileController {

    @PostMapping("/upload/image")
    public ResponseEntity<ApiResponse> uploadImage(
            @AuthenticationPrincipal MemberLoginInfo memberLoginInfo,
            @RequestParam(value = "file", required = false) MultipartFile multipartFile) {
        return ResponseEntity.ok()
                .build();
    }
}
