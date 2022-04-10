package com.atdd.practice.filestore.infrastructure;

import com.atdd.practice.common.exception.NotInstanceException;
import com.atdd.practice.filestore.application.exception.InvalidImageFileExtensionException;
import com.atdd.practice.filestore.application.exception.InvalidVideoFileExtensionException;

import java.util.Arrays;

public class FileExtensionValidator {

    private static final String[] IMAGE_EXTENSIONS = {"jpeg", "png"};

    private static final String[] VIDEO_EXTENSIONS = {"mp4", "avi"};

    public FileExtensionValidator() {
        throw new NotInstanceException();
    }

    public static void validateExtension(FileType fileType, String extension) {
        switch (fileType) {
            case IMAGE:
                validateImageExtension(extension);
                break;
            case VIDEO:
                validateVideoExtension(extension);
                break;
        }
    }

    private static void validateImageExtension(String extension) {
        if (!Arrays.asList(IMAGE_EXTENSIONS).contains(extension)) {
            throw new InvalidImageFileExtensionException();
        }
    }

    private static void validateVideoExtension(String extension) {
        if (!Arrays.asList(VIDEO_EXTENSIONS).contains(extension)) {
            throw new InvalidVideoFileExtensionException();
        }
    }
}
