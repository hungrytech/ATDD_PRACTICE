package com.atdd.practice.member.application.exception;

import com.atdd.practice.common.exception.AuthorizeException;

public class NotRegisteredRefreshToken extends AuthorizeException {

    private static final String MESSAGE = "등록된 토큰이 아닙니다.";

    public NotRegisteredRefreshToken() {
        super(MESSAGE);
    }
}
