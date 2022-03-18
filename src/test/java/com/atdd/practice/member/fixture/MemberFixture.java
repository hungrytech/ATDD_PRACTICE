package com.atdd.practice.member.fixture;

import com.atdd.practice.member.domain.Member;

import java.lang.reflect.Field;

public class MemberFixture {

    public static final String ADMIN_MEMBER_EMAIL = "xorals9448@gmail.com";

    public static final String ADMIN_MEMBER_PASSWORD = "test2134";

    public static final String ADMIN_MEMBER_ENCODED_PASSWORD = "dfnq2p21fenfef";

    public static final Member MEMBER_ADMIN = Member.createAdmin(ADMIN_MEMBER_EMAIL, ADMIN_MEMBER_ENCODED_PASSWORD);

    public static final Member MEMBER_CUSTOMER = customer();

    public static final String CUSTOMER_MEMBER_EMAIL = "junco1440@naver.com";
    public static final String CUSTOMER_MEMBER_PASSWORD = "test1234";

    public static final String CUSTOMER_MEMBER_ENCODED_PASSWORD = "adof2nfedofelfe";

    private static Member customer() {
        try {
            Member customer = Member.createCustomer(CUSTOMER_MEMBER_EMAIL, CUSTOMER_MEMBER_PASSWORD);

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
