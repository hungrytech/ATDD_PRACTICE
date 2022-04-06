package com.atdd.practice.member.application.exception;

import com.atdd.practice.common.exception.InvalidRequestException;

public class DuplicateEmailException extends InvalidRequestException {

    private static final String MESSAGE = "이미 존재하는 아이디(이메일) 입니다.";

    public DuplicateEmailException() {
        super(MESSAGE);
    }
}
