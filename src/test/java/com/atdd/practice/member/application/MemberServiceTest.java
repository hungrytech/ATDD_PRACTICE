package com.atdd.practice.member.application;

import com.atdd.practice.common.config.ServiceTest;
import com.atdd.practice.member.application.exception.DuplicateEmailException;
import com.atdd.practice.member.domain.Member;
import com.atdd.practice.member.domain.MemberRepository;
import com.atdd.practice.member.presentation.dto.request.MemberJoinRequest;
import com.atdd.practice.member.presentation.dto.response.MemberJoinResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.atdd.practice.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class MemberServiceTest extends ServiceTest {

    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, passwordEncoder);
    }

    @DisplayName("회원가입 요청이 들어오면 회원 정보를 저장한다.")
    @Test
    void save_member() {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, CUSTOMER_MEMBER_PASSWORD);

        // when
        given(passwordEncoder.encode(anyString()))
                .willReturn(CUSTOMER_MEMBER_ENCODED_PASSWORD);
        given(memberRepository.save(any(Member.class)))
                .willReturn(MEMBER_CUSTOMER);

        MemberJoinResponse memberJoinResponse = memberService.join(memberJoinRequest);

        // then
        assertThat(memberJoinResponse.getId()).isNotNull();
    }

    @DisplayName("이메일이 증복될 경우 회원 가입에 실패한다.")
    @Test
    void save_member_exception_because_of_exist_email() {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, CUSTOMER_MEMBER_PASSWORD);

        // when
        given(memberRepository.existsByEmail(anyString())).willReturn(true);

        // then
        assertThatThrownBy(() -> memberService.join(memberJoinRequest)).isInstanceOf(DuplicateEmailException.class);
    }
}