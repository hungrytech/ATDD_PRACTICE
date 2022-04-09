package com.atdd.practice.filestore.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class FileStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @Embedded
    private FileInfo fileInfo;

    private FileStore(Long memberId, FileInfo fileInfo) {
        this.memberId = memberId;
        this.fileInfo = fileInfo;
    }

    public static FileStore createFileStore(Long memberId, FileInfo fileInfo) {
        return new FileStore(memberId, fileInfo);
    }
}
