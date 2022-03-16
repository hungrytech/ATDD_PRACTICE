package com.atdd.practice.common.security.infrastructure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtils {

    private final String secretKey;

    private final String identifiedKey;

    private final Duration accessTokenExpiredIn;

    private final Duration refreshTokenExpiredIn;

    public JwtUtils(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.identified-key}") String identifiedKey,
            @Value("${jwt.access-token-expired-in}") Duration accessTokenExpiredIn,
            @Value("${jwt.refresh-token-expired-in}") Duration refreshTokenExpiredIn) {
        this.secretKey = secretKey;
        this.identifiedKey = identifiedKey;
        this.accessTokenExpiredIn = accessTokenExpiredIn;
        this.refreshTokenExpiredIn = refreshTokenExpiredIn;
    }

    public AccessToken createAccessToken(String identificationValue, LocalDateTime currentDateTime) {
        Date expiredDateTime = createExpiredTimeByCurrentDate(accessTokenExpiredIn, currentDateTime);
        return new AccessToken(
                createToken(identificationValue, expiredDateTime),
                expiredDateTime);
    }

    public RefreshToken createRefreshToken(String identificationValue, LocalDateTime currentDateTime) {
        Date expiredDateTime = createExpiredTimeByCurrentDate(refreshTokenExpiredIn, currentDateTime);
        return new RefreshToken(
                createToken(identificationValue, expiredDateTime),
                expiredDateTime);
    }

    private Date createExpiredTimeByCurrentDate(Duration tokenExpiredIn, LocalDateTime currentDateTime) {
        return Date.from(currentDateTime
                .plus(tokenExpiredIn)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private Claims createClaims(String identificationValue) {
        Claims claims = Jwts.claims();
        claims.put(identifiedKey, identificationValue);
        return claims;
    }

    private String createToken(String identificationValue, Date expiredTime) {
        return Jwts.builder()
                .setClaims(createClaims(identificationValue))
                .setIssuedAt(new Date())
                .setExpiration(expiredTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return String.valueOf(claims.get(identifiedKey));
    }
}
