package com.atdd.practice.filestore.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileInfo {

    private String path;

    private String extension;

    private String name;

    private FileInfo(String path, String extension, String name) {
        this.path = path;
        this.extension = extension;
        this.name = name;
    }

    public static FileInfo of(String path, String extension, String name) {
        return new FileInfo(path, extension, name);
    }
}
