package com.atdd.practice.filestore.application;

import com.atdd.practice.common.config.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class FileServiceTest extends ServiceTest {

    private FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
    }

    @DisplayName("이미지 파일 업로드에 성공한다.")
    @Test
    void 이미지_업로드_성공() throws IOException {
        // given
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/file/image_test.jpeg");
        MultipartFile mockMultipartFile = new MockMultipartFile(
                file.getName(),
                file.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                new FileInputStream(file));

        String destination = System.getProperty("user.dir") + "/src/test/resources/file/test/";

        // when
        FileUploadResult filUploadResult = fileService.uploadFile(mockMultipartFile, FileType.IMAGE, destination);

        // then
        assertAll(
                () -> assertThat(filUploadResult.getPath()).isNotNull(),
                () -> assertThat(filUploadResult.getOriginalFileName()).isEqualTo(mockMultipartFile.getOriginalFilename())
        );
    }
}
