package com.atdd.practice.common.config;

import com.atdd.practice.member.presentation.dto.request.MemberJoinRequest;
import com.atdd.practice.member.presentation.dto.request.MemberLoginRequest;
import com.atdd.practice.member.presentation.dto.response.MemberLoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static com.atdd.practice.member.fixture.MemberFixture.CUSTOMER_MEMBER_EMAIL;
import static com.atdd.practice.member.fixture.MemberFixture.CUSTOMER_MEMBER_PASSWORD;
import static com.atdd.practice.member.presentation.AuthControllerTest.로그인_요청;
import static com.atdd.practice.member.presentation.MemberControllerTest.회원가입;

@Import(FileAcceptanceTestConfig.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    private static final String API_RESPONSE_DATA = "data";

    private static final String API_EXCEPTION_MESSAGE = "error";

    protected static final String AUTHENTICATION_TYPE = "Bearer ";

    protected MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, CUSTOMER_MEMBER_PASSWORD);

    protected MemberLoginRequest memberLoginRequest = new MemberLoginRequest(CUSTOMER_MEMBER_EMAIL, CUSTOMER_MEMBER_PASSWORD);

    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void cleanUpDatabase() {
        databaseCleanUp.execute();
    }

    protected <T> T convertToData(ExtractableResponse<Response> response, Class<T> type) {
        return response.jsonPath().getObject(API_RESPONSE_DATA, type);
    }

    protected String extractSuccessMessage(ExtractableResponse<Response> response) {
        return response.jsonPath().get("message");
    }

    protected String extractExceptionMessage(ExtractableResponse<Response> response) {
        return response.jsonPath()
                .get(API_EXCEPTION_MESSAGE);
    }

    protected String getAccessToken() {
        회원가입(memberJoinRequest);

        return AUTHENTICATION_TYPE + convertToData(
                로그인_요청(this.memberLoginRequest), MemberLoginResponse.class)
                .getAccessToken();
    }
}


