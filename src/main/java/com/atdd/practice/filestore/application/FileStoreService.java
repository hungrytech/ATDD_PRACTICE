package com.atdd.practice.filestore.application;

import com.atdd.practice.filestore.domain.FileInfo;
import com.atdd.practice.filestore.domain.FileStore;
import com.atdd.practice.filestore.domain.FileStoreRepository;
import com.atdd.practice.filestore.infrastructure.FileType;
import com.atdd.practice.filestore.infrastructure.FileUploadResult;
import com.atdd.practice.member.application.MemberLoginInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStoreService {

    private final FileService fileService;

    private final FileStoreRepository fileStoreRepository;

    public FileStoreService(FileService fileService, FileStoreRepository fileStoreRepository) {
        this.fileService = fileService;
        this.fileStoreRepository = fileStoreRepository;
    }

    @Transactional
    public void uploadImageFile(MemberLoginInfo memberLoginInfo,  MultipartFile requestFile) throws IOException {
        FileUploadResult fileUploadResult = fileService.uploadFile(
                requestFile,
                FileType.IMAGE,
                memberLoginInfo.getEmail());

        FileStore fileStore = FileStore.createFileStore(
                memberLoginInfo.getId(),
                FileInfo.of(
                        fileUploadResult.getPath(),
                        fileUploadResult.getExtension(),
                        fileUploadResult.getOriginalFileName()));

        fileStoreRepository.save(fileStore);
    }

    public void uploadVideoFile(MemberLoginInfo memberLoginInfo, MultipartFile requestFile) throws IOException {
        FileUploadResult fileUploadResult = fileService.uploadFile(
                requestFile,
                FileType.VIDEO,
                memberLoginInfo.getEmail());

        FileStore fileStore = FileStore.createFileStore(
                memberLoginInfo.getId(),
                FileInfo.of(
                        fileUploadResult.getPath(),
                        fileUploadResult.getExtension(),
                        fileUploadResult.getOriginalFileName()));

        fileStoreRepository.save(fileStore);
    }
}
