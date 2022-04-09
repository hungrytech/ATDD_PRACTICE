package com.atdd.practice.filestore.application.exception;

import com.atdd.practice.common.exception.InvalidRequestException;

public class InvalidFileRequestException extends InvalidRequestException {

    private static final String MESSAGE = "잘못된 파일 업로드 요청입니다.";

    public InvalidFileRequestException() {
        super(MESSAGE);
    }
}
