package com.atdd.practice.member.application;

import com.atdd.practice.common.security.infrastructure.AccessToken;
import com.atdd.practice.common.security.infrastructure.JwtUtils;
import com.atdd.practice.common.security.infrastructure.RefreshToken;
import com.atdd.practice.member.application.exception.InvalidLoginInfoException;
import com.atdd.practice.member.domain.Member;
import com.atdd.practice.member.domain.MemberRepository;
import com.atdd.practice.member.domain.RefreshTokenRepository;
import com.atdd.practice.member.presentation.dto.request.MemberLoginRequest;
import com.atdd.practice.member.presentation.dto.response.MemberLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    @Transactional
    public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) {
        Member member = memberRepository.findMemberByEmail(memberLoginRequest.getEmail())
                .orElseThrow(InvalidLoginInfoException::new);

        if (!passwordEncoder.matches(memberLoginRequest.getPassword(), member.getPassword())) {
            throw new InvalidLoginInfoException();
        }

        AccessToken accessToken = jwtUtils.createAccessToken(member.getEmail(), LocalDateTime.now());

        RefreshToken savedRefreshToken = refreshTokenRepository.save(
                jwtUtils.createRefreshToken(member.getEmail(), LocalDateTime.now()));

        return new MemberLoginResponse(accessToken, savedRefreshToken);
    }
}
