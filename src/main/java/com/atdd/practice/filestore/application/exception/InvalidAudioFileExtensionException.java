package com.atdd.practice.filestore.application.exception;

public class InvalidAudioFileExtensionException extends InvalidFileExtensionException {

    private static final String MESSAGE = "받을 수 있는 오디오 파일의 확장자가 아닙니다.";

    public InvalidAudioFileExtensionException() {
        super(MESSAGE);
    }
}
