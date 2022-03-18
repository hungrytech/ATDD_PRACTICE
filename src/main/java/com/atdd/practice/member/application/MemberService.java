package com.atdd.practice.member.application;

import com.atdd.practice.member.application.exception.DuplicateEmailException;
import com.atdd.practice.member.domain.Member;
import com.atdd.practice.member.domain.MemberRepository;
import com.atdd.practice.member.presentation.dto.request.MemberJoinRequest;
import com.atdd.practice.member.presentation.dto.response.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberJoinResponse join(MemberJoinRequest memberJoinRequest) {
        if (memberRepository.existsByEmail(memberJoinRequest.getEmail())) {
            throw new DuplicateEmailException();
        }

        String encodedPassword = passwordEncoder.encode(memberJoinRequest.getPassword());

        Member savedMember = memberRepository.save(memberJoinRequest.toEntity(encodedPassword));

        return new MemberJoinResponse(savedMember);
    }
}
