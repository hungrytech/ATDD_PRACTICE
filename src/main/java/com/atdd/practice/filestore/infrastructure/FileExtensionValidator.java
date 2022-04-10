package com.atdd.practice.filestore.infrastructure;

import com.atdd.practice.common.exception.NotInstanceException;
import com.atdd.practice.filestore.application.exception.InvalidAudioFileExtensionException;
import com.atdd.practice.filestore.application.exception.InvalidImageFileExtensionException;
import com.atdd.practice.filestore.application.exception.InvalidVideoFileExtensionException;

import java.util.Arrays;

public class FileExtensionValidator {

    private static final String[] IMAGE_EXTENSIONS = {"jpeg", "png"};

    private static final String[] VIDEO_EXTENSIONS = {"mp4", "avi"};

    private static final String[] AUDIO_EXTENSIONS = {"wav", "mp3"};

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
            case AUDIO:
                validateAudioExtension(extension);
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

    private static void validateAudioExtension(String extension) {
        if (!Arrays.asList(AUDIO_EXTENSIONS).contains(extension)) {
            throw new InvalidAudioFileExtensionException();
        }
    }
}
