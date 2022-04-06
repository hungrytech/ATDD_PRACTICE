package com.atdd.practice.member.presentation.dto.request;

import com.atdd.practice.member.domain.Member;
import com.atdd.practice.member.domain.Password;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberJoinRequest {

    private String email;

    private String password;

    public MemberJoinRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        Password.validatePassword(this.password);
        return Member.createCustomer(email, Password.of(passwordEncoder.encode(password)));
    }
}
