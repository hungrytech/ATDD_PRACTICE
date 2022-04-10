package com.atdd.practice.filestore.application;

import com.atdd.practice.common.config.ServiceTestForFile;
import com.atdd.practice.filestore.application.exception.InvalidFileExtensionException;
import com.atdd.practice.filestore.application.exception.InvalidVideoFileExtensionException;
import com.atdd.practice.filestore.domain.FileStoreRepository;
import com.atdd.practice.filestore.infrastructure.LocalFileDirMaker;
import com.atdd.practice.filestore.infrastructure.LocalFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import static com.atdd.practice.common.fixture.MemberLoginInfoFixture.TEST_MEMBER_LOGIN_INFO;
import static com.atdd.practice.filestore.fixture.TestFilePath.*;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileStoreServiceTest extends ServiceTestForFile {

    private FileStoreService fileStoreService;

    @Mock
    private FileStoreRepository fileStoreRepository;

    @BeforeEach
    void setUp() {
        fileStoreService = new FileStoreService(
                new LocalFileService(new LocalFileDirMaker(TEST_UPLOAD_FILES_DIR_PATH)),
                fileStoreRepository);
    }

    @DisplayName("이미지 파일 업로드에 성공한다.")
    @Test
    void 이미지_업로드_성공() throws Exception {
        // given
        MultipartFile mockMultipartFile = new MockMultipartFile(
                PNG_IMAGE_FILE.getName(),
                PNG_IMAGE_FILE.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                new FileInputStream(PNG_IMAGE_FILE));

        // when then
        assertThatCode(() -> fileStoreService.uploadImageFile(TEST_MEMBER_LOGIN_INFO, mockMultipartFile))
                .doesNotThrowAnyException();
    }

    @DisplayName("저장할 수 있는 이미지 확장자만 받을 수 있다.")
    @Test
    void 이미지_업로드_실패_잘못된_확장자() throws Exception {
        // given
        MultipartFile mockMultipartFile = new MockMultipartFile(
                INVALID_EXTENSION_IMAGE_FILE.getName(),
                INVALID_EXTENSION_IMAGE_FILE.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                new FileInputStream(INVALID_EXTENSION_IMAGE_FILE));

        // when then
        assertThatThrownBy(() -> fileStoreService.uploadImageFile(TEST_MEMBER_LOGIN_INFO, mockMultipartFile))
                .isInstanceOf(InvalidFileExtensionException.class);
    }

    @DisplayName("비디오 파일 업로드에 성공한다.")
    @Test
    void 비디오_업로드_성공() throws Exception {
        // given
        MultipartFile mockMultipartFile = new MockMultipartFile(
                MP4_VIDEO_FILE.getName(),
                MP4_VIDEO_FILE.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                new FileInputStream(MP4_VIDEO_FILE));

        // when then
        assertThatCode(() -> fileStoreService.uploadVideoFile(TEST_MEMBER_LOGIN_INFO, mockMultipartFile))
                .doesNotThrowAnyException();
    }

    @DisplayName("저장할 수 있는 동영상 확장자만 받을 수 있다.")
    @Test
    void 비디오_업로드_실패() throws Exception {
        // given
        MultipartFile mockMultipartFile = new MockMultipartFile(
                INVALID_EXTENSION_VIDEO_FILE.getName(),
                INVALID_EXTENSION_VIDEO_FILE.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                new FileInputStream(INVALID_EXTENSION_VIDEO_FILE));

        // when then
        assertThatThrownBy(() -> fileStoreService.uploadVideoFile(TEST_MEMBER_LOGIN_INFO, mockMultipartFile))
                .isInstanceOf(InvalidVideoFileExtensionException.class);
    }
}
