package com.atdd.practice.member.application.exception;

import com.atdd.practice.common.exception.InvalidRequestException;

public class DuplicateEmailException extends InvalidRequestException {

    private static final String MESSAGE = "증복된 이메일 입니다.";

    public DuplicateEmailException() {
        super(MESSAGE);
    }
}
