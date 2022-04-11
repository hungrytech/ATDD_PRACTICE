package com.atdd.practice.filestore.infrastructure;

import com.atdd.practice.filestore.application.exception.InvalidAudioFileExtensionException;
import com.atdd.practice.filestore.application.exception.InvalidFileExtensionException;
import com.atdd.practice.filestore.application.exception.InvalidImageFileExtensionException;
import com.atdd.practice.filestore.application.exception.InvalidVideoFileExtensionException;

import java.util.Arrays;

public enum FileType {
    IMAGE(new String[]{"png", "jpeg"}, new InvalidImageFileExtensionException()),
    VIDEO(new String[]{"mp4", "avi"}, new InvalidVideoFileExtensionException()),
    AUDIO(new String[]{"wav", "mp3"}, new InvalidAudioFileExtensionException());

    private final String[] extensions;

    private final InvalidFileExtensionException invalidFileExtensionException;

    FileType(String[] extensions, InvalidFileExtensionException invalidFileExtensionException) {
        this.extensions = extensions;
        this.invalidFileExtensionException = invalidFileExtensionException;
    }

    public void validateExtension(String extension) {
        if (!containsValidExtension(extension)) {
            throw this.invalidFileExtensionException;
        }
    }

    private boolean containsValidExtension(String extension) {
        return Arrays.asList(this.extensions)
                .contains(extension);
    }
}
