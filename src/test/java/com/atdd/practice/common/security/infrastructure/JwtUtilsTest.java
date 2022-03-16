package com.atdd.practice.common.security.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JwtUtilsTest {

    private static final String USER_EMAIL = "xorals9448@gmail.com";

    private static final LocalDateTime DATE_TIME = LocalDateTime.of(6000, 3, 16, 14, 20);

    private static final long ACCESS_TOKEN_EXPIRED_IN = 30L;

    private static final long REFRESH_TOKEN_EXPIRED_IN = 3L;

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        String secretKey = "test-secret-key";
        String identifiedKey = "email";
        jwtUtils = new JwtUtils(
                secretKey,
                identifiedKey,
                Duration.ofMinutes(ACCESS_TOKEN_EXPIRED_IN),
                Duration.ofDays(REFRESH_TOKEN_EXPIRED_IN));
    }

    @DisplayName("accessToken을 생성한다.")
    @Test
    void create_access_token() {
        // when
        AccessToken accessToken = jwtUtils.createAccessToken(USER_EMAIL, DATE_TIME);

        // then
        assertAll(
                () -> assertThat(accessToken.getToken()).isNotNull(),
                () -> assertThat(accessToken.getExpiredDateTime()).isEqualTo(DATE_TIME.plusMinutes(ACCESS_TOKEN_EXPIRED_IN))
        );
    }

    @DisplayName("refreshToken을 생성한다.")
    @Test
    void create_refresh_token() {
        // when
        RefreshToken refreshToken = jwtUtils.createRefreshToken(USER_EMAIL, DATE_TIME);

        // then
        assertAll(
                () -> assertThat(refreshToken.getToken()).isNotNull(),
                () -> assertThat(refreshToken.getExpiredDateTime()).isEqualTo(DATE_TIME.plusDays(REFRESH_TOKEN_EXPIRED_IN))
        );
    }

    @DisplayName("token에서 식별 값을 가져온다.")
    @Test
    void get_identified_value_in_token() {
        // given
        AccessToken accessToken = jwtUtils.createAccessToken(USER_EMAIL, DATE_TIME);

        // when
        String identifiedValue = jwtUtils.parseToken(accessToken.getToken());

        // then
        assertThat(identifiedValue).isEqualTo(USER_EMAIL);
    }

}