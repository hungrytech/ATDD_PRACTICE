package com.atdd.practice.member.domain;

import lombok.Getter;

public enum Role {
    CUSTOMER("ROLE_CUSTOMER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return this.role;
    }
}
