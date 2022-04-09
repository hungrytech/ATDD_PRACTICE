package com.atdd.practice.filestore.infrastructure;

import com.atdd.practice.filestore.application.FileService;
import com.atdd.practice.filestore.application.exception.InvalidFileRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class LocalFileService implements FileService {

    private final LocalFileDirMaker localFileDirMaker;

    public LocalFileService(LocalFileDirMaker localFileDirMaker) {
        this.localFileDirMaker = localFileDirMaker;
    }

    @Override
    public FileUploadResult uploadFile(
            MultipartFile multipartFile,
            FileType fileType,
            String memberEmail) throws IOException {

        if (multipartFile == null) {
            throw new InvalidFileRequestException();
        }

        String extension = extractExtension(multipartFile);
        FileExtensionValidator.validateExtension(fileType, extension);

        String fileName = createIdentifierName(multipartFile);
        multipartFile.transferTo(new File(createDestination(memberEmail) + fileName));

        return new FileUploadResult(fileName, extension, multipartFile.getOriginalFilename());
    }

    private String createDestination(String memberEmail) {
        return localFileDirMaker.createTargetMemberDir(memberEmail);
    }

    private String extractExtension(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().indexOf(".") + 1);
    }

    private String createIdentifierName(MultipartFile multipartFile) {
        return UUID.randomUUID() + multipartFile.getOriginalFilename();
    }
}
