package com.atdd.practice.filestore.application;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    public FileUploadResult uploadFile(MultipartFile multipartFile, FileType fileType, String destination) throws IOException {
        String fileName = createIdentifierName(multipartFile);
        File file = new File(destination + fileName);
        multipartFile.transferTo(file);
        return new FileUploadResult(fileName, multipartFile.getOriginalFilename());
    }

    private String createIdentifierName(MultipartFile multipartFile) {
        return UUID.randomUUID() + multipartFile.getOriginalFilename();
    }
}
