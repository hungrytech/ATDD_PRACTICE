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

import static org.assertj.core.api.Assertions.assertThatCode;

public class FileStoreServiceTest extends ServiceTest {

    private FileStoreService fileStoreService;

    @BeforeEach
    void setUp() {
        fileStoreService = new FileStoreService();
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

        // when then
        assertThatCode(() -> fileStoreService.uploadImageFile(1L, mockMultipartFile))
                .doesNotThrowAnyException();
    }

}
