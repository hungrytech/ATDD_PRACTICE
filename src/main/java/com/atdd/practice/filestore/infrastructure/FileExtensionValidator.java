package com.atdd.practice.filestore.infrastructure;

import com.atdd.practice.common.exception.NotInstanceException;
import com.atdd.practice.filestore.application.exception.InvalidImageFileExtensionException;

import java.util.Arrays;

public class FileExtensionValidator {

    private static final String[] IMAGE_EXTENSIONS = {"jpeg", "png"};

    public FileExtensionValidator() {
        throw new NotInstanceException();
    }

    public static void validateExtension(FileType fileType, String extension) {
        switch (fileType) {
            case IMAGE:
                validateImageExtension(extension);
                break;
        }
    }

    private static void validateImageExtension(String extension) {
        if (!Arrays.asList(IMAGE_EXTENSIONS).contains(extension)) {
            throw new InvalidImageFileExtensionException();
        }
    }
}
