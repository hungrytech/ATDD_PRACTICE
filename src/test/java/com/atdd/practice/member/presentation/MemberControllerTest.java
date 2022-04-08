package com.atdd.practice.member.presentation;

import com.atdd.practice.common.config.AcceptanceTest;
import com.atdd.practice.member.presentation.dto.request.MemberJoinRequest;
import com.atdd.practice.member.presentation.dto.response.MemberJoinResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.atdd.practice.member.fixture.MemberFixture.CUSTOMER_MEMBER_EMAIL;
import static com.atdd.practice.member.fixture.MemberFixture.CUSTOMER_MEMBER_PASSWORD;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberControllerTest extends AcceptanceTest {

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

    // 1. 아이디 증복 체크 요청을 보낸다
    // 2. 아이디 증복 여부를 검사한다.
    // 3. 아이디 증복 결과를 반환한다
    @DisplayName("아이디 증복여부 검사 성공 사용 가능한 이메일")
    @Test
    void 아이디_증복여부_검사_성공_사용가능_이메일() {
        // when
        ExtractableResponse<Response> response = 아이디_증복_검사(CUSTOMER_MEMBER_EMAIL);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(extractSuccessMessage(response)).isEqualTo("사용 가능한 아이디(이메일)입니다.")
        );
    }

    @DisplayName("아이디 증복여부 검사 성공 사용 불가능한 이메일")
    @Test
    void 아이디_증복여부_검사_성공_존재하는_이메일() {
        //given
        회원가입(memberJoinRequest);

        // when
        ExtractableResponse<Response> response = 아이디_증복_검사(CUSTOMER_MEMBER_EMAIL);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(extractSuccessMessage(response)).isEqualTo("이미 존재하는 아이디(이메일)입니다.")
        );
    }

    @DisplayName("아이디 증복여부 검사 실패 이메일 형식에 알맞지 않음")
    @ParameterizedTest
    @ValueSource(strings = {
            "dfdfefg.com",
            "dfdf@dfdsvd",
    })
    void 아이디_증복여부_검사_실패_잘못된_이메일_형식(String requestEmail) {
        // when
        ExtractableResponse<Response> response = 아이디_증복_검사(requestEmail);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extractExceptionMessage(response)).isEqualTo("이메일 형식에 맞지 않습니다.")
        );
    }

    @DisplayName("아이디 증복여부 검사 실패 요청한 이메일이 공백이거나 null")
    @ParameterizedTest
    @NullAndEmptySource
    void 아이디_증복검사_실패_공백_Null(String requestEmail) {
        // when
        ExtractableResponse<Response> response = 아이디_증복_검사(requestEmail);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(extractExceptionMessage(response)).isEqualTo("이메일 형식에 맞지 않습니다.")
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

    private ExtractableResponse<Response> 아이디_증복_검사(String requestEmail) {
        return given()
                .param("email", requestEmail)
                .when()
                .get("/api/v1/members")
                .then().log().all()
                .extract();
    }
}
