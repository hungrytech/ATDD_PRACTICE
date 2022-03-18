package com.atdd.practice.common.security.infrastructure;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@RedisHash("refreshToken")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    private String token;

    @TimeToLive
    private long expirationIn;

    private LocalDateTime expiredDateTime;

    public RefreshToken(String refreshToken, long expirationIn, Date expiredDate) {
        this.token = refreshToken;
        this.expirationIn = expirationIn;
        this.expiredDateTime = expiredDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
