package com.atdd.practice.member.application;

import com.atdd.practice.common.config.ServiceTest;
import com.atdd.practice.member.application.exception.DuplicateEmailException;
import com.atdd.practice.member.application.exception.InvalidEmailException;
import com.atdd.practice.member.application.exception.InvalidPasswordException;
import com.atdd.practice.member.domain.Member;
import com.atdd.practice.member.domain.MemberRepository;
import com.atdd.practice.member.presentation.dto.request.MemberJoinRequest;
import com.atdd.practice.member.presentation.dto.response.EmailDuplicateResultMessage;
import com.atdd.practice.member.presentation.dto.response.MemberJoinResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
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
    void 회원가입_성공() {
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

    @DisplayName("이메일 형식이 잘못될 경우 회원가입에 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "daffe@dfs",
            "dfsdf.dfs",
            "dsfsfdge"
    })
    void 회원가입_실패_잘못된_이메일형식(String requestEmail) {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(requestEmail, CUSTOMER_MEMBER_PASSWORD);

        // when
        given(memberRepository.existsByEmail(anyString())).willReturn(false);

        // then
        assertThatThrownBy(() -> memberService.join(memberJoinRequest)).isInstanceOf(InvalidEmailException.class);
    }


    @DisplayName("이메일이 증복될 경우 회원가입에 실패한다.")
    @Test
    void 회원가입_실패_이메일_증복() {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, CUSTOMER_MEMBER_PASSWORD);

        // when
        given(memberRepository.existsByEmail(anyString())).willReturn(true);

        // then
        assertThatThrownBy(() -> memberService.join(memberJoinRequest)).isInstanceOf(DuplicateEmailException.class);
    }

    @DisplayName("비밀번호가 빈값일 경우 회원가입에 실패한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void 회원가입_실패_비밀번호_빈값(String password) {
        // when
        given(memberRepository.existsByEmail(anyString())).willReturn(false);
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, password);

        // then
        assertThatThrownBy(() -> memberService.join(memberJoinRequest)).isInstanceOf(InvalidPasswordException.class);
    }

    @DisplayName("비밀번호안에 특수문자가 포함되지 않을경우 회원가입에 실패한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "dfed@fsfddd#",
            "2d3f3425ddd",
            "dfdsfe234dffd2",
            "dfdfdsagadsf"
    })
    void 회원가입_실패_특수문자_포함되지_않음(String password) {
        // when
        given(memberRepository.existsByEmail(anyString())).willReturn(false);
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, password);

        // then
        assertThatThrownBy(() -> memberService.join(memberJoinRequest)).isInstanceOf(InvalidPasswordException.class);
    }

    @DisplayName("아이디 증복여부 검사 성공 사용 가능한 이메일")
    @Test
    void 아이디_증복검사_성공_사용가능_이메일() {
        //given
        given(memberRepository.existsByEmail(CUSTOMER_MEMBER_EMAIL)).willReturn(false);

        // when
        EmailDuplicateResultMessage resultMessage = memberService.checkOfDuplicateEmail(CUSTOMER_MEMBER_EMAIL);

        // then
        assertThat(resultMessage.getMessage()).isEqualTo("사용 가능한 아이디(이메일)입니다.");
    }

    @DisplayName("아이디 증복여부 검사 성공 사용 불가능한 이메일")
    @Test
    void 아이디_증복검사_성공_시용불가능_이메일() {
        // given
        given(memberRepository.existsByEmail(CUSTOMER_MEMBER_EMAIL)).willReturn(true);

        // when
        EmailDuplicateResultMessage resultMessage = memberService.checkOfDuplicateEmail(CUSTOMER_MEMBER_EMAIL);

        // then
        assertThat(resultMessage.getMessage()).isEqualTo("이미 존재하는 아이디(이메일)입니다.");
    }

    @DisplayName("아이디 증복여부 검사 실패 이메일 형식에 알맞지 않음")
    @ParameterizedTest
    @ValueSource(strings = {
            "dfdfefg.com",
            "dfdf@dfdsvd"
    })
    void 아이디_증복검사_실패_잘못된_이메일_형식(String requestEmail) {
        assertThatThrownBy(() -> memberService.checkOfDuplicateEmail(requestEmail))
                .isInstanceOf(InvalidEmailException.class);
    }

    @DisplayName("아이디 증복여부 검사 실패 요청한 이메일이 공백이거나 null")
    @ParameterizedTest
    @NullAndEmptySource
    void 아이디_증복검사_실패_공백_Null(String requestEmail) {
        assertThatThrownBy(() -> memberService.checkOfDuplicateEmail(requestEmail))
                .isInstanceOf(InvalidEmailException.class);
    }
}