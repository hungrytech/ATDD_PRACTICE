package com.atdd.practice.filestore.application.exception;

import com.atdd.practice.common.exception.InvalidRequestException;

public abstract class InvalidFileExtensionException extends InvalidRequestException {

    public InvalidFileExtensionException(String message) {
        super(message);
    }
}
