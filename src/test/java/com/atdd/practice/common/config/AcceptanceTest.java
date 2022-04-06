package com.atdd.practice.common.config;

import com.atdd.practice.common.apiresponse.DefaultErrorTemplate;
import com.atdd.practice.common.apiresponse.ErrorTemplate;
import com.atdd.practice.member.presentation.dto.request.MemberJoinRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedHashMap;
import java.util.List;

import static com.atdd.practice.member.fixture.MemberFixture.CUSTOMER_MEMBER_EMAIL;
import static com.atdd.practice.member.fixture.MemberFixture.CUSTOMER_MEMBER_PASSWORD;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    private static final String API_RESPONSE_DATA = "data";

    private static final String API_EXCEPTION_MESSAGE = "error";

    protected MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, CUSTOMER_MEMBER_PASSWORD);

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

    protected String extractExceptionMessage(ExtractableResponse<Response> response) {
        return response.jsonPath()
                .get(API_EXCEPTION_MESSAGE);
    }
}


