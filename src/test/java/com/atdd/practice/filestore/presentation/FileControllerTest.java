package com.atdd.practice.filestore.presentation;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.File;

import static com.atdd.practice.filestore.fixture.TestFilePath.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class FileControllerTest extends FileAcceptanceTest {


    // 1. 사용자가 이미지 파일 업로드 요청을 보낸다
    // 2. 이미지 파일 확장자를 확인한다.
    // 3. 파일 업로드 완료 200 응답을 보낸다.
    @DisplayName("이미지 파일 업로드에 성공한다.")
    @Test
    void 업로드_성공_이미지_파일() {
        // given when
        ExtractableResponse<Response> response = 이미지_업로드_요청(JPEG_IMAGE_FILE);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("저장할 수 있는 이미지 확장자만 받을 수 있다.")
    @Test
    void 이미지_업로드_실패_이미지_파일_잘못된_확장자() {
        // given when
        ExtractableResponse<Response> response = 이미지_업로드_요청(INVALID_EXTENSION_IMAGE_FILE);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    // 1. 사용자가 동영상 파일 업로드 요청을 보낸다
    // 2. 동영상 파일 확장자를 확인한다.
    // 3. 파일 업로드 완료 200 응답을 보낸다.
    @DisplayName("동영상 파일 업로드에 성공한다.")
    @Test
    void 업로드_성공_동영상_파일() {
        // given when
        ExtractableResponse<Response> response = 동영상_업로드_요청(MP4_VIDEO_FILE);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("저장할 수 있는 동영상 확장자만 받을 수 있다.")
    @Test
    void 업로드_실패_동영상_파일_잘못된_확장자() {
        // given when
        ExtractableResponse<Response> response = 동영상_업로드_요청(INVALID_EXTENSION_VIDEO_FILE);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    // 1. 사용자가 오디오 파일 업로드 요청을 보낸다
    // 2. 동영상 파일 확장자를 확인한다.
    // 3. 파일 업로드 완료 200 응답을 보낸다.
    @DisplayName("오디오 파일 업로드에 성공한다.")
    @Test
    void 업로드_성공_오디오_파일() {
        // given when
        ExtractableResponse<Response> response = 오디오_업로드_요청(MP3_VIDEO_FILE);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("저장할 수 있는 오디오 확장자만 받을 수 있다.")
    @Test
    void 업로드_실패_오디오_파일_잘못된_확장자() {
        // given when
        ExtractableResponse<Response> response = 오디오_업로드_요청(INVALID_EXTENSION_AUDIO_FILE);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> 이미지_업로드_요청(File requestFile) {
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .header(HttpHeaders.AUTHORIZATION, getAccessToken())
                .multiPart("file", requestFile)
                .log().all()
                .when()
                .post("/api/v1/upload/image")
                .then()
                .log()
                .all()
                .extract();
        return response;
    }

    private ExtractableResponse<Response> 동영상_업로드_요청(File requestFile) {
        return given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .header(HttpHeaders.AUTHORIZATION, getAccessToken())
                .multiPart("file", requestFile)
                .log().all()
                .when()
                .post("/api/v1/upload/video")
                .then()
                .log()
                .all()
                .extract();
    }

    private ExtractableResponse<Response> 오디오_업로드_요청(File requestFile) {
        return given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .header(HttpHeaders.AUTHORIZATION, getAccessToken())
                .multiPart("file", requestFile)
                .log().all()
                .when()
                .post("/api/v1/upload/audio")
                .then()
                .log()
                .all()
                .extract();
    }
}
