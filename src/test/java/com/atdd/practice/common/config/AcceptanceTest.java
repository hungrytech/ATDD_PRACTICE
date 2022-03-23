package com.atdd.practice.common.config;

import com.atdd.practice.member.presentation.dto.request.MemberJoinRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static com.atdd.practice.member.fixture.MemberFixture.CUSTOMER_MEMBER_EMAIL;
import static com.atdd.practice.member.fixture.MemberFixture.CUSTOMER_MEMBER_PASSWORD;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    protected MemberJoinRequest memberJoinRequest = new MemberJoinRequest(CUSTOMER_MEMBER_EMAIL, CUSTOMER_MEMBER_PASSWORD);

    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
}


