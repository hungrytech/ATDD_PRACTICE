package com.atdd.practice.filestore.application;

import com.atdd.practice.filestore.infrastructure.FileType;
import com.atdd.practice.filestore.infrastructure.FileUploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    FileUploadResult uploadFile(MultipartFile multipartFile, FileType fileType, String memberEmail) throws IOException;
}
