package com.atdd.practice.member.application.exception;

public class InvalidLoginInfoException extends RuntimeException {

    private static final String MESSAGE = "아이디 혹은 비밀번호가 알맞지 앖습니다.";

    public InvalidLoginInfoException() {
        super(MESSAGE);
    }
}
