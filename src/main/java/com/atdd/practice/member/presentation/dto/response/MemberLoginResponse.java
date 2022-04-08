package com.atdd.practice.member.presentation.dto.response;

import com.atdd.practice.common.security.infrastructure.AccessToken;
import com.atdd.practice.common.security.infrastructure.RefreshToken;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLoginResponse {

    private String accessToken;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime accessTokenIn;

    private String refreshToken;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime refreshTokenIn;

    public MemberLoginResponse(AccessToken accessToken, RefreshToken refreshToken) {
        this.accessToken = accessToken.getToken();
        this.accessTokenIn = accessToken.getExpiredDateTime();
        this.refreshToken = refreshToken.getToken();
        this.refreshTokenIn = refreshToken.getExpiredDateTime();
    }
}
