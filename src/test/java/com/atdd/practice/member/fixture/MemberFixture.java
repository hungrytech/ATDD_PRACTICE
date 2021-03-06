package com.atdd.practice.member.fixture;

import com.atdd.practice.common.security.infrastructure.RefreshToken;
import com.atdd.practice.member.domain.Email;
import com.atdd.practice.member.domain.Member;
import com.atdd.practice.member.domain.Password;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Date;

public class MemberFixture {

    // AUTH
    public static final String SECRET_KEY = "test-secret-key";

    public static final String IDENTIFIED_VALUE = "email";

    public static final Duration ACCESS_TOKEN_EXPIRED_IN = Duration.ofMinutes(30L);

    public static final Duration REFRESH_TOKEN_EXPIRED_IN = Duration.ofDays(3L);

    public static final RefreshToken MEMBER_ADMIN_REFRESHTOKEN = new RefreshToken(
            "dfnp1dofenaf2dofae",
            REFRESH_TOKEN_EXPIRED_IN.toMillis(),
            new Date());

    // MEMBER
    public static final String ADMIN_MEMBER_EMAIL = "xorals9448@gmail.com";

    public static final String ADMIN_MEMBER_PASSWORD = "test2134%$";

    public static final String ADMIN_MEMBER_ENCODED_PASSWORD = "dfnq2p21fenfef";

    public static final Member MEMBER_ADMIN = Member.createAdmin(Email.of(ADMIN_MEMBER_EMAIL), Password.of(ADMIN_MEMBER_ENCODED_PASSWORD));

    public static final Member MEMBER_CUSTOMER = customer();

    public static final String CUSTOMER_MEMBER_EMAIL = "junco1440@naver.com";

    public static final String CUSTOMER_MEMBER_PASSWORD = "test1234#";

    public static final String CUSTOMER_MEMBER_ENCODED_PASSWORD = "adof2nfedofelfe";

    private static Member customer() {
        try {
            Member customer = Member.createCustomer(Email.of(CUSTOMER_MEMBER_EMAIL), Password.of(CUSTOMER_MEMBER_PASSWORD));

            Field id = customer.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(customer, 2L);
            return customer;
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }

        throw new RuntimeException();
    }
}
