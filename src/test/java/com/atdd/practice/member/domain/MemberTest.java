package com.atdd.practice.member.domain;

import com.atdd.practice.member.application.exception.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
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

}
