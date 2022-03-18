package com.atdd.practice.member.presentation.dto.request;

import com.atdd.practice.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberJoinRequest {

    private String email;

    private String password;

    public MemberJoinRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member toEntity(String encodedPassword) {
        return Member.createCustomer(email, encodedPassword);
    }
}
