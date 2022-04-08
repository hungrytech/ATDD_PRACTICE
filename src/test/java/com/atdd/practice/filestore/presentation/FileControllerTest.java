package com.atdd.practice.filestore.presentation;

import com.atdd.practice.common.config.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class FileControllerTest extends AcceptanceTest {


    // 1. 사용자가 이미지 파일 업로드 요청을 보낸다
    // 2. 이미지 파일 확장자를 확인한다.
    // 3. 파일 업로드 완료 200 응답을 보낸다.
    @DisplayName("이미지 파일 업로드에 성공한다.")
    @Test
    void 이미지_업로드_성공() {
        // given
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/file/image_test.jpeg");

        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .multiPart("file", file)
                .log().all()
                .when()
                .post("/api/v1/upload/image")
                .then()
                .log()
                .all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

}
