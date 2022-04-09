package com.atdd.practice.common.exception;

public class ApplicationException extends RuntimeException {

    private static final String MESSAGE = "시스템 오류입니다. 관리자에게 문의하여 주십시오.";

    public ApplicationException() {
        super(MESSAGE);
    }
}
