package com.atdd.practice.common.fixture;

import com.atdd.practice.member.application.MemberLoginInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class MemberLoginInfoFixture {

    public static final MemberLoginInfo TEST_MEMBER_LOGIN_INFO = new MemberLoginInfo(
            1L,
            "xorals9448@gmail.com",
            "",
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
}
