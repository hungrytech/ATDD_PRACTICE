package com.atdd.practice.common.security.infrastructure;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
public class AccessToken {

    private final String token;

    private final LocalDateTime expiredDateTime;

    public AccessToken(String accessToken, Date expiredDate) {
        this.token = accessToken;
        this.expiredDateTime = expiredDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
