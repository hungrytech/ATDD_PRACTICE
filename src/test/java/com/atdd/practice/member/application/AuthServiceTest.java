package com.atdd.practice.member.application;

import com.atdd.practice.common.config.ServiceTest;
import com.atdd.practice.common.security.infrastructure.JwtUtils;
import com.atdd.practice.common.security.infrastructure.RefreshToken;
import com.atdd.practice.member.application.exception.InvalidLoginInfoException;
import com.atdd.practice.member.domain.MemberRepository;
import com.atdd.practice.member.domain.RefreshTokenRepository;
import com.atdd.practice.member.presentation.dto.request.MemberLoginRequest;
import com.atdd.practice.member.presentation.dto.response.MemberLoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @BeforeEach
    void setUp() {
        authService = new AuthService(
                memberRepository,
                refreshTokenRepository,
                passwordEncoder,
                new JwtUtils(SECRET_KEY,
                        IDENTIFIED_VALUE,
                        ACCESS_TOKEN_EXPIRED_IN,
                        REFRESH_TOKEN_EXPIRED_IN));
    }

    @DisplayName("로그인 성공 - accessToken과 refreshToken을 반환한다.")
    @Test
    void login() {
        // given
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(ADMIN_MEMBER_EMAIL, ADMIN_MEMBER_PASSWORD);

        // when
        given(memberRepository.findMemberByEmail(anyString()))
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
        given(memberRepository.findMemberByEmail(anyString()))
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
        given(memberRepository.findMemberByEmail(anyString()))
                .willReturn(Optional.of(MEMBER_ADMIN));
        given(passwordEncoder.matches(anyString(), anyString()))
                .willReturn(false);

        // then
        assertThatThrownBy(() -> authService.login(memberLoginRequest)).isInstanceOf(InvalidLoginInfoException.class);
    }


}