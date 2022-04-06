package com.atdd.practice.member.application.exception;

import com.atdd.practice.common.exception.InvalidRequestException;

public class InvalidLoginInfoException extends InvalidRequestException {

    private static final String MESSAGE = "일치하는 계정 정보가 없습니다.";

    public InvalidLoginInfoException() {
        super(MESSAGE);
    }
}
