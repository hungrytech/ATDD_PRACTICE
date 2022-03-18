package com.atdd.practice.member.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLoginRequest {

    private String email;

    private String password;

    public MemberLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
