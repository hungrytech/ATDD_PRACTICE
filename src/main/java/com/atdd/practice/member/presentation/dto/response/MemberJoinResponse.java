package com.atdd.practice.member.presentation.dto.response;

import com.atdd.practice.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberJoinResponse {

    private Long id;

    private String email;

    public MemberJoinResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
    }
}
