package com.atdd.practice.member.domain;

import com.atdd.practice.member.application.exception.InvalidEmailException;
import com.atdd.practice.member.application.exception.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MemberTest {

    @DisplayName("비밀번호가 공백일 경우 예외를 발생시킨다.")
    @ParameterizedTest
    @NullAndEmptySource
    void 비밀번호_공백(String password) {
        assertThatThrownBy(() -> Password.validatePassword(password))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @DisplayName("비밀번호에 특수문자와 숫자가 포함되지 않은경우 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "dfedfsfddd#",
            "2d3f3425ddd",
            "dfdsfe234dffd2",
            "dfdfdsagadsf"
    })
    void 비밀번호_특수문자와_숫자를_포함하지_않음(String password) {
        assertThatThrownBy(() -> Password.validatePassword(password))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @DisplayName("아이디 증복여부 검사 실패 이메일 형식에 알맞지 않음")
    @ParameterizedTest
    @ValueSource(strings = {
            "dfdfefg.com",
            "dfdf@dfdsvd",
    })
    void 아이디_증복검사_실패_잘못된_이메일_형식(String requestEmail) {
        assertThatThrownBy(() -> Email.validateForm(requestEmail))
                .isInstanceOf(InvalidEmailException.class);
    }

    @DisplayName("아이디 증복여부 검사 실패 요청한 이메일이 공백이거나 null")
    @ParameterizedTest
    @NullAndEmptySource
    void 아이디_증복검사_실패_공백_Null(String requestEmail) {
        assertThatThrownBy(() -> Email.validateForm(requestEmail))
                .isInstanceOf(InvalidEmailException.class);
    }
}
