package com.atdd.practice.member.application;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class MemberLoginInfo extends User {

    private final Long id;

    public MemberLoginInfo(
            Long id,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public String getEmail() {
        return getUsername();
    }
}
