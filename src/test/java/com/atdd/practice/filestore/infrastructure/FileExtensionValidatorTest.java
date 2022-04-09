package com.atdd.practice.filestore.infrastructure;

import com.atdd.practice.filestore.application.exception.InvalidImageFileExtensionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FileExtensionValidatorTest {

    @DisplayName("지원하는 이미지확장자를 가질경우 성공")
    @ParameterizedTest
    @ValueSource(strings = {
            "image1.png",
            "image2.jpeg"
    })
    void 이미지_확장자_검사_성공(String fileName) {
        assertThatCode(() -> FileExtensionValidator.validateExtension(FileType.IMAGE, extractExtension(fileName)))
                .doesNotThrowAnyException();
    }

    @DisplayName("지원하지않는 이미지확장자를 가질경우 실패")
    @ParameterizedTest
    @ValueSource(strings = {
            "image1.cvd",
            "image2.mov"
    })
    void 이미지_확장자_검사_실패(String fileName) {
        assertThatThrownBy(() -> FileExtensionValidator.validateExtension(FileType.IMAGE, extractExtension(fileName)))
                .isInstanceOf(InvalidImageFileExtensionException.class);
    }

    private String extractExtension(String fileName) {
        return fileName.substring(fileName.indexOf(".") + 1);
    }

}