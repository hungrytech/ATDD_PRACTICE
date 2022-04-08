package com.atdd.practice.member.application.exception;

import com.atdd.practice.common.exception.InvalidRequestException;

public class InvalidEmailException extends InvalidRequestException {

    private static final String MESSAGE = "이메일 형식에 맞지 않습니다.";

    public InvalidEmailException() {
        super(MESSAGE);
    }
}
