package com.atdd.practice.common.security.infrastructure;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
public class RefreshToken {

    private final String token;

    private final LocalDateTime expiredDateTime;

    public RefreshToken(String accessToken, Date expiredDate) {
        this.token = accessToken;
        this.expiredDateTime = expiredDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
