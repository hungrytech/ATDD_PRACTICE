package com.atdd.practice.member.presentation;

import com.atdd.practice.common.config.AcceptanceTest;
import com.atdd.practice.member.presentation.dto.request.MemberJoinRequest;
import com.atdd.practice.member.presentation.dto.response.MemberJoinResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.atdd.practice.member.fixture.MemberFixture.CUSTOMER_MEMBER_EMAIL;
import static com.atdd.practice.member.fixture.MemberFixture.CUSTOMER_MEMBER_PASSWORD;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberControllerTest extends AcceptanceTest {

    @Autowired
    ObjectMapper objectMapper;

    // 1. 회원가입 요청을 보낸다
    // 2. 이메일 증복여부를 검사한다.
    // 3. 이메일 증복여부 검사가 성공할 경우 회원정보를 저장한다.
    // 4. 증복여부 검사가 실패할 경우 예외를 발생시킨다.
    // 4. ok 응답을 발송한다.
    @DisplayName("정상적인 회원가입 요청이 들어오면 회원 정보를 저장한다.")
    @Test
    void 회원가입_성공() {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, CUSTOMER_MEMBER_PASSWORD);

        // when
        ExtractableResponse<Response> response = 회원가입(memberJoinRequest);
        MemberJoinResponse memberJoinResponse = convertToData(response, MemberJoinResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(memberJoinResponse.getId()).isNotNull(),
                () -> assertThat(memberJoinResponse.getEmail()).isEqualTo(memberJoinRequest.getEmail())
        );
    }

    @DisplayName("아이디가 증복이 될 경우 회원가입 실패 응답을 보낸다.")
    @Test
    void 회원가입_실패_아이디_증복() {
        // given
        회원가입_성공();
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, CUSTOMER_MEMBER_PASSWORD);

        // when
        ExtractableResponse<Response> response = 회원가입(memberJoinRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extractExceptionMessage(response)).isEqualTo("이미 존재하는 아이디(이메일) 입니다.")
        );

    }


    @DisplayName("잘못 된 회원가입 요청이 들어오면 회원가입 실패 응답을 보낸다.")
    @ParameterizedTest
    @NullAndEmptySource
    void 회원가입_실패_비밀번호_공백_또는_null(String password) {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, password);

        // when
        ExtractableResponse<Response> response = 회원가입(memberJoinRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extractExceptionMessage(response)).isEqualTo("비밀번호는 특수문자와 숫자를 반드시 포함한 8자리 이상의 영문자로 작성해야 합니다.")
        );

    }

    @DisplayName("잘못 된 회원가입 요청이 들어오면 회원가입 실패 응답을 보낸다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "dfedfsfddd#",
            "2d3f3425ddd",
            "dfdsfe234dffd2",
            "dfdfdsagadsf"
    })
    void 회원가입_실패_비밀번호_특수문자_포함하지_않음(String password) {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, password);

        // when
        ExtractableResponse<Response> response = 회원가입(memberJoinRequest);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extractExceptionMessage(response)).isEqualTo("비밀번호는 특수문자와 숫자를 반드시 포함한 8자리 이상의 영문자로 작성해야 합니다.")
        );
    }

    public static ExtractableResponse<Response> 회원가입(MemberJoinRequest memberJoinRequest) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE).log().all()
                .body(memberJoinRequest)
                .when()
                .post("/api/v1/members")
                .then().log().all()
                .extract();
    }
}
