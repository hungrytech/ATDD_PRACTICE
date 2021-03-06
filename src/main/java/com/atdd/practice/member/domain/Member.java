package com.atdd.practice.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Member(Email email, Password password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static Member createCustomer(Email email, Password password) {
        return new Member(email, password, Role.CUSTOMER);
    }

    public static Member createAdmin(Email email, Password password) {
        return new Member(email, password, Role.ADMIN);
    }

    public String getPassword() {
        return this.password.getValue();
    }

    public String getEmail() {
        return this.email.getValue();
    }
}
