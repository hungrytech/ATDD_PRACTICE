package com.atdd.practice.member.application.exception;

import com.atdd.practice.common.exception.InvalidRequestException;

public class InvalidLoginInfoException extends InvalidRequestException {

    private static final String MESSAGE = "아이디 또는 비밀번호가 잘못되었습니다.";

    public InvalidLoginInfoException() {
        super(MESSAGE);
    }
}
