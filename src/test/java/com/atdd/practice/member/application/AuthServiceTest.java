package com.atdd.practice.member.application;

import com.atdd.practice.common.config.ServiceTest;
import com.atdd.practice.common.security.infrastructure.JwtUtils;
import com.atdd.practice.common.security.infrastructure.RefreshToken;
import com.atdd.practice.member.application.exception.InvalidLoginInfoException;
import com.atdd.practice.member.application.exception.NotRegisteredRefreshToken;
import com.atdd.practice.member.domain.MemberRepository;
import com.atdd.practice.member.domain.RefreshTokenRepository;
import com.atdd.practice.member.presentation.dto.request.MemberLoginRequest;
import com.atdd.practice.member.presentation.dto.response.MemberLoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static com.atdd.practice.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class AuthServiceTest extends ServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    private AuthService authService;

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils(SECRET_KEY,
                IDENTIFIED_VALUE,
                ACCESS_TOKEN_EXPIRED_IN,
                REFRESH_TOKEN_EXPIRED_IN);
        authService = new AuthService(
                memberRepository,
                refreshTokenRepository,
                passwordEncoder,
                jwtUtils);
    }

    @DisplayName("로그인 성공 - accessToken과 refreshToken을 반환한다.")
    @Test
    void login() {
        // given
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(ADMIN_MEMBER_EMAIL, ADMIN_MEMBER_PASSWORD);

        // when
        given(memberRepository.findMemberByEmailValue(anyString()))
                .willReturn(Optional.of(MEMBER_ADMIN));
        given(passwordEncoder.matches(anyString(), anyString()))
                .willReturn(true);
        given(refreshTokenRepository.save(any(RefreshToken.class)))
                .willReturn(MEMBER_ADMIN_REFRESHTOKEN);

        MemberLoginResponse memberLoginResponse = authService.login(memberLoginRequest);

        // then
        assertAll(
                () -> assertThat(memberLoginResponse.getAccessToken()).isNotNull(),
                () -> assertThat(memberLoginResponse.getRefreshToken()).isNotNull()
        );
    }

    @DisplayName("로그인 실패 - 회원을 찾을 수 없는 경우")
    @Test
    void login_exception_not_found_member() {
        // given
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(ADMIN_MEMBER_EMAIL, ADMIN_MEMBER_PASSWORD);

        // when
        given(memberRepository.findMemberByEmailValue(anyString()))
                .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> authService.login(memberLoginRequest)).isInstanceOf(InvalidLoginInfoException.class);
    }

    @DisplayName("로그인 실패 - 비밀번호가 맞지 않은 경우")
    @Test
    void login_exception_not_match_password() {
        // given
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(ADMIN_MEMBER_EMAIL, ADMIN_MEMBER_PASSWORD);

        // when
        given(memberRepository.findMemberByEmailValue(anyString()))
                .willReturn(Optional.of(MEMBER_ADMIN));
        given(passwordEncoder.matches(anyString(), anyString()))
                .willReturn(false);

        // then
        assertThatThrownBy(() -> authService.login(memberLoginRequest)).isInstanceOf(InvalidLoginInfoException.class);
    }

    @DisplayName("리프레쉬토큰 재발급 성공 - accessToken과 refreshToken을 반환한다.")
    @Test
    void 토큰_재발급_성공() {
        // given
        String refreshToken = "refreshToken";

        // when
        given(refreshTokenRepository.findById(refreshToken))
                .willReturn(Optional.of(
                        jwtUtils.createRefreshToken(
                                refreshToken,
                                LocalDateTime.of(2340, 10, 10, 10, 32))
                ));
        given(refreshTokenRepository.save(any(RefreshToken.class)))
                .willReturn(MEMBER_ADMIN_REFRESHTOKEN);

        MemberLoginResponse memberLoginResponse = authService.reissueRefreshToken(refreshToken);

        // then
        assertAll(
                () -> assertThat(memberLoginResponse.getAccessToken()).isNotNull(),
                () -> assertThat(memberLoginResponse.getRefreshToken()).isNotNull()
        );
    }

    @DisplayName("토큰재발급 실패 - 등록된 리프레쉬토큰이 아닌 경우")
    @Test
    void 토큰_재발급_실패() {
        // given
        String refreshToken = "refreshToken";

        // when
        given(refreshTokenRepository.findById(refreshToken))
                .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> authService.reissueRefreshToken(refreshToken))
                .isInstanceOf(NotRegisteredRefreshToken.class);
    }
}