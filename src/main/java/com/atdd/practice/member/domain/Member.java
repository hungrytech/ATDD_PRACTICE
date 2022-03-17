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
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static Member createCustomer(String email, String password) {
        return new Member(email, password, Role.CUSTOMER);
    }

    public static Member createAdmin(String email, String password) {
        return new Member(email, password, Role.ADMIN);
    }
}
