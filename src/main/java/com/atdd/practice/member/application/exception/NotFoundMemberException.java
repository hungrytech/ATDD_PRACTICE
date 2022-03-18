package com.atdd.practice.member.application.exception;

import com.atdd.practice.common.exception.InvalidRequestException;

public class NotFoundMemberException extends InvalidRequestException {

    private static final String MESSAGE = "찾는 회원이 없습니다.";

    public NotFoundMemberException() {
        super(MESSAGE);
    }
}
