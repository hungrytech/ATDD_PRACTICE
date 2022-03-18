package com.atdd.practice.member.presentation;

import com.atdd.practice.common.config.AcceptanceTest;
import com.atdd.practice.member.presentation.dto.request.MemberJoinRequest;
import com.atdd.practice.member.presentation.dto.response.MemberJoinResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    void join() {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, CUSTOMER_MEMBER_PASSWORD);

        // when
        ExtractableResponse<Response> response = join(memberJoinRequest);
        MemberJoinResponse memberJoinResponse = response.jsonPath().getObject("data", MemberJoinResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(memberJoinResponse.getId()).isNotNull(),
                () -> assertThat(memberJoinResponse.getEmail()).isEqualTo(memberJoinRequest.getEmail())
        );
    }

    private ExtractableResponse<Response> join(MemberJoinRequest memberJoinRequest) {
        return given()
                .contentType(MediaType.APPLICATION_JSON_VALUE).log().all()
                .body(memberJoinRequest)
                .when()
                .post("/api/v1/members")
                .then().log().all()
                .extract();
    }
}
