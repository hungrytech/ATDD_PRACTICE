package com.atdd.practice.member.application;

import com.atdd.practice.common.config.ServiceTest;
import com.atdd.practice.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.atdd.practice.member.fixture.MemberFixture.MEMBER_ADMIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class JwtAuthenticationServiceTest extends ServiceTest {

    @Mock
    private MemberRepository memberRepository;

    private JwtAuthenticationService jwtAuthenticationService;

    @BeforeEach
    void setUp() {
        jwtAuthenticationService = new JwtAuthenticationService(memberRepository);
    }

    @DisplayName("인증된 유저정보를 가져온다")
    @Test
    void find_authorized_user_info() {
        // given
        String userEmail = "xorals9448@gmail.com";

        // when
        given(memberRepository.findMemberByEmailValue(userEmail)).willReturn(Optional.of(MEMBER_ADMIN));

        UserDetails userDetails = jwtAuthenticationService.loadUserByUsername(userEmail);

        // then
        assertThat(userDetails.getUsername()).isEqualTo(userEmail);
    }

}