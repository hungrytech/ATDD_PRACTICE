package com.atdd.practice.member.application.exception;

import com.atdd.practice.common.exception.InvalidRequestException;

public class InvalidPasswordException extends InvalidRequestException {

    private static final String MESSAGE = "비밀번호는 특수문자와 숫자를 반드시 포함한 8자리 이상의 영문자로 작성해야 합니다.";

    public InvalidPasswordException() {
        super(MESSAGE);
    }
}
