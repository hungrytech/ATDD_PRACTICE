package com.atdd.practice.filestore.application.exception;

public class InvalidImageFileExtensionException extends InvalidFileExtensionException {

    private static final String MESSAGE = "받을 수 있는 이미지 파일의 확장자가 아닙니다.";

    public InvalidImageFileExtensionException() {
        super(MESSAGE);
    }
}
