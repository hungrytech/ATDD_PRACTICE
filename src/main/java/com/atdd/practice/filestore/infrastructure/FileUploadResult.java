package com.atdd.practice.filestore.infrastructure;

import lombok.Getter;

@Getter
public class FileUploadResult {

    private final String path;

    private final String extension;

    private final String originalFileName;

    public FileUploadResult(String path, String extension, String originalFileName) {
        this.path = path;
        this.extension = extension;
        this.originalFileName = originalFileName;
    }
}
