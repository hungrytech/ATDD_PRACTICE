package com.atdd.practice.member.application.exception;

import com.atdd.practice.common.exception.InvalidRequestException;

public class InvalidLoginInfoException extends InvalidRequestException {

    private static final String MESSAGE = "아이디 혹은 비밀번호가 알맞지 앖습니다.";

    public InvalidLoginInfoException() {
        super(MESSAGE);
    }
}
