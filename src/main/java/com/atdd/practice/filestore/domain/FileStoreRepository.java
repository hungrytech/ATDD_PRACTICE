package com.atdd.practice.filestore.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStoreRepository extends JpaRepository<FileStore, Long> {
}
