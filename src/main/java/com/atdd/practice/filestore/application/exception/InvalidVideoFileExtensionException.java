package com.atdd.practice.filestore.application.exception;

public class InvalidVideoFileExtensionException extends InvalidFileExtensionException {

    private static final String MESSAGE = "받을 수 있는 동영상 파일의 확장자가 아닙니다.";

    public InvalidVideoFileExtensionException() {
        super(MESSAGE);
    }
}
