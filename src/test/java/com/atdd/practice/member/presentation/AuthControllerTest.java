package com.atdd.practice.member.presentation;

import com.atdd.practice.common.config.AcceptanceTest;
import com.atdd.practice.member.presentation.dto.request.MemberLoginRequest;
import com.atdd.practice.member.presentation.dto.response.MemberLoginResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.atdd.practice.member.fixture.MemberFixture.*;
import static com.atdd.practice.member.presentation.MemberControllerTest.join;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AuthControllerTest extends AcceptanceTest {

    //1. 회원의 이메일과 패스워드로 로그인을 요청한다.
    //2. 요청한 회원이 가입이 되어있는지 확인한다.
    //3. accessToken을 발급한다.
    //4. refreshToken을 발급한다.
    //5. redis에 refreshToken을 저장한다.
    //6. accessToken, refreshToken 과 각각 만료시간을 반환한다.
    @DisplayName("정상적인 회원 로그인 성공")
    @Test
    void login() {
        // given
        join(memberJoinRequest);
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(CUSTOMER_MEMBER_EMAIL, CUSTOMER_MEMBER_PASSWORD);

        // when
        ExtractableResponse<Response> response = login(memberLoginRequest);

        MemberLoginResponse memberLoginResponse = response.jsonPath().getObject("data", MemberLoginResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(memberLoginResponse.getAccessToken()).isNotNull(),
                () -> assertThat(memberLoginResponse.getRefreshToken()).isNotNull()
        );
    }

    public static ExtractableResponse<Response> login(MemberLoginRequest memberLoginRequest) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .log().all()
                .body(memberLoginRequest)
                .when()
                .post("/api/v1/login")
                .then()
                .log().all()
                .extract();
    }

}
