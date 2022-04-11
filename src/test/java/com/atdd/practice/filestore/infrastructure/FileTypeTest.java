package com.atdd.practice.filestore.infrastructure;

import com.atdd.practice.filestore.application.exception.InvalidAudioFileExtensionException;
import com.atdd.practice.filestore.application.exception.InvalidImageFileExtensionException;
import com.atdd.practice.filestore.application.exception.InvalidVideoFileExtensionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileTypeTest {

    @DisplayName("지원하는 이미지확장자를 가질경우 성공")
    @ParameterizedTest
    @ValueSource(strings = {
            "image1.png",
            "image2.jpeg"
    })
    void 이미지_확장자_검사_성공(String fileName) {
        assertThatCode(() -> FileType.IMAGE.validateExtension(extractExtension(fileName)))
                .doesNotThrowAnyException();
    }

    @DisplayName("지원하지않는 이미지확장자를 가질경우 실패")
    @ParameterizedTest
    @ValueSource(strings = {
            "image1.cvd",
            "image2.mov"
    })
    void 이미지_확장자_검사_실패(String fileName) {
        assertThatThrownBy(() -> FileType.IMAGE.validateExtension(extractExtension(fileName)))
                .isInstanceOf(InvalidImageFileExtensionException.class);
    }

    @DisplayName("지원하는 비디오확장자를 가질경우 성공")
    @ParameterizedTest
    @ValueSource(strings = {
            "video1.mp4",
            "image2.avi"
    })
    void 비디오_확장자_검사_성공(String fileName) {
        assertThatCode(() -> FileType.VIDEO.validateExtension(extractExtension(fileName)))
                .doesNotThrowAnyException();
    }

    @DisplayName("지원하지않는 비디오확장자를 가질경우 실패")
    @ParameterizedTest
    @ValueSource(strings = {
            "video1.mov",
            "video2.mkv"
    })
    void 비디오_확장자_검사_실패(String fileName) {
        assertThatThrownBy(() -> FileType.VIDEO.validateExtension(extractExtension(fileName)))
                .isInstanceOf(InvalidVideoFileExtensionException.class);
    }

    @DisplayName("지원하는 오디오확장자를 가질경우 성공")
    @ParameterizedTest
    @ValueSource(strings = {
            "audio1.wav",
            "audio2.mp3"
    })
    void 오디오_확장자_검사_성공(String fileName) {
        assertThatCode(() -> FileType.AUDIO.validateExtension(extractExtension(fileName)))
                .doesNotThrowAnyException();
    }

    @DisplayName("지원하지않는 오디오확장자오 가질경우 실패")
    @ParameterizedTest
    @ValueSource(strings = {
            "video1.ogg",
            "video2.vox"
    })
    void 오디오_확장자_검사_실패(String fileName) {
        assertThatThrownBy(() -> FileType.AUDIO.validateExtension(extractExtension(fileName)))
                .isInstanceOf(InvalidAudioFileExtensionException.class);
    }

    private String extractExtension(String fileName) {
        return fileName.substring(fileName.indexOf(".") + 1);
    }
}