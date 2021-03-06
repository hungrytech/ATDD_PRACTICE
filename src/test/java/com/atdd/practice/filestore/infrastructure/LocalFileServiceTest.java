package com.atdd.practice.filestore.infrastructure;

import com.atdd.practice.common.config.ServiceTestForFile;
import com.atdd.practice.filestore.application.exception.InvalidAudioFileExtensionException;
import com.atdd.practice.filestore.application.exception.InvalidFileExtensionException;
import com.atdd.practice.filestore.application.exception.InvalidFileRequestException;
import com.atdd.practice.filestore.application.exception.InvalidVideoFileExtensionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static com.atdd.practice.common.fixture.MemberLoginInfoFixture.TEST_MEMBER_LOGIN_INFO;
import static com.atdd.practice.filestore.fixture.TestFilePath.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LocalFileServiceTest extends ServiceTestForFile {

    private LocalFileService localFileService;

    @BeforeEach
    void setUp() {
        localFileService = new LocalFileService(new LocalFileDirMaker(TEST_UPLOAD_FILES_DIR_PATH));
    }

    @DisplayName("이미지 파일 업로드에 성공한다.")
    @Test
    void 이미지_업로드_성공() throws IOException {
        // given
        MultipartFile mockMultipartFile = new MockMultipartFile(
                JPEG_IMAGE_FILE.getName(),
                JPEG_IMAGE_FILE.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                new FileInputStream(JPEG_IMAGE_FILE));

        // when
        FileUploadResult filUploadResult = localFileService.uploadFile(
                mockMultipartFile,
                FileType.IMAGE,
                TEST_MEMBER_LOGIN_INFO.getEmail());

        // then
        assertAll(
                () -> assertThat(filUploadResult.getPath()).isNotNull(),
                () -> assertThat(filUploadResult.getOriginalFileName()).isEqualTo(mockMultipartFile.getOriginalFilename())
        );
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
        assertThatThrownBy(() -> localFileService.uploadFile(mockMultipartFile, FileType.IMAGE, TEST_MEMBER_LOGIN_INFO.getEmail()))
                .isInstanceOf(InvalidFileExtensionException.class);
    }

    @DisplayName("파일을 전송하지 않을 경우 예외가 발생한다.")
    @Test
    void 파일_업로드_실패_파일_객체가_null() {
        assertThatThrownBy(() -> localFileService.uploadFile(null, FileType.IMAGE, TEST_MEMBER_LOGIN_INFO.getEmail()))
                .isInstanceOf(InvalidFileRequestException.class);
    }

    @DisplayName("동영상 파일 업로드에 성공한다.")
    @Test
    void 동영상_업로드_성공() throws IOException {
        // given
        MultipartFile mockMultipartFile = new MockMultipartFile(
                MP4_VIDEO_FILE.getName(),
                MP4_VIDEO_FILE.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                new FileInputStream(MP4_VIDEO_FILE));

        // when
        FileUploadResult filUploadResult = localFileService.uploadFile(
                mockMultipartFile,
                FileType.VIDEO,
                TEST_MEMBER_LOGIN_INFO.getEmail());

        // then
        assertAll(
                () -> assertThat(filUploadResult.getPath()).isNotNull(),
                () -> assertThat(filUploadResult.getOriginalFileName()).isEqualTo(mockMultipartFile.getOriginalFilename())
        );
    }

    @DisplayName("저장할 수 있는 동영상 확장자만 받을 수 있다.")
    @Test
    void 동영상_업로드_실패_잘못된_확장자() throws Exception {
        // given
        MultipartFile mockMultipartFile = new MockMultipartFile(
                INVALID_EXTENSION_VIDEO_FILE.getName(),
                INVALID_EXTENSION_VIDEO_FILE.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                new FileInputStream(INVALID_EXTENSION_VIDEO_FILE));

        // when then
        assertThatThrownBy(() -> localFileService.uploadFile(mockMultipartFile, FileType.VIDEO, TEST_MEMBER_LOGIN_INFO.getEmail()))
                .isInstanceOf(InvalidVideoFileExtensionException.class);
    }

    @DisplayName("오디오 파일 업로드에 성공한다.")
    @Test
    void 오디오_업로드_성공() throws IOException {
        // given
        MultipartFile mockMultipartFile = new MockMultipartFile(
                MP3_VIDEO_FILE.getName(),
                MP3_VIDEO_FILE.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                new FileInputStream(MP3_VIDEO_FILE));

        // when
        FileUploadResult filUploadResult = localFileService.uploadFile(
                mockMultipartFile,
                FileType.AUDIO,
                TEST_MEMBER_LOGIN_INFO.getEmail());

        // then
        assertAll(
                () -> assertThat(filUploadResult.getPath()).isNotNull(),
                () -> assertThat(filUploadResult.getOriginalFileName()).isEqualTo(mockMultipartFile.getOriginalFilename())
        );
    }

    @DisplayName("저장할 수 있는 오디오 확장자만 받을 수 있다.")
    @Test
    void 오디오_업로드_실패_잘못된_확장자() throws Exception {
        // given
        MultipartFile mockMultipartFile = new MockMultipartFile(
                INVALID_EXTENSION_AUDIO_FILE.getName(),
                INVALID_EXTENSION_AUDIO_FILE.getName(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                new FileInputStream(INVALID_EXTENSION_AUDIO_FILE));

        // when then
        assertThatThrownBy(() -> localFileService.uploadFile(mockMultipartFile, FileType.AUDIO, TEST_MEMBER_LOGIN_INFO.getEmail()))
                .isInstanceOf(InvalidAudioFileExtensionException.class);
    }
}
