package com.atdd.practice.filestore.application;

import lombok.Getter;

@Getter
public class FileUploadResult {

    private final String path;

    private final String originalFileName;

    public FileUploadResult(String path, String originalFileName) {
        this.path = path;
        this.originalFileName = originalFileName;
    }
}
