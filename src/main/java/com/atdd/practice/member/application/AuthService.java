package com.atdd.practice.member.application;

import com.atdd.practice.member.domain.Member;
import com.atdd.practice.member.domain.MemberRepository;
import com.atdd.practice.member.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String identificationValue) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByEmail(identificationValue)
                .orElseThrow(() -> new RuntimeException());
        return new MemberLoginInfo(member.getId(), member.getEmail(), "", extractRole(member.getRole()));
    }

    private List<GrantedAuthority> extractRole(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
    }
}
