package com.atdd.practice.member.application;

import com.atdd.practice.common.security.infrastructure.AccessToken;
import com.atdd.practice.common.security.infrastructure.JwtUtils;
import com.atdd.practice.common.security.infrastructure.RefreshToken;
import com.atdd.practice.member.application.exception.InvalidLoginInfoException;
import com.atdd.practice.member.application.exception.NotRegisteredRefreshToken;
import com.atdd.practice.member.domain.Member;
import com.atdd.practice.member.domain.MemberRepository;
import com.atdd.practice.member.domain.RefreshTokenRepository;
import com.atdd.practice.member.presentation.dto.request.MemberLoginRequest;
import com.atdd.practice.member.presentation.dto.response.MemberLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    @Transactional
    public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) {
        Member member = memberRepository.findMemberByEmailValue(memberLoginRequest.getEmail())
                .orElseThrow(InvalidLoginInfoException::new);

        if (!passwordEncoder.matches(memberLoginRequest.getPassword(), member.getPassword())) {
            throw new InvalidLoginInfoException();
        }

        AccessToken accessToken = jwtUtils.createAccessToken(member.getEmail(), now());

        RefreshToken savedRefreshToken = refreshTokenRepository.save(
                jwtUtils.createRefreshToken(member.getEmail(), now()));

        return new MemberLoginResponse(accessToken, savedRefreshToken);
    }

    @Transactional
    public MemberLoginResponse reissueRefreshToken(String refreshToken) {
        RefreshToken existingRefreshToken = refreshTokenRepository.findById(refreshToken)
                .orElseThrow(NotRegisteredRefreshToken::new);

        String memberEmail = jwtUtils.parseToken(existingRefreshToken.getToken());
        refreshTokenRepository.delete(existingRefreshToken);

        AccessToken accessToken = jwtUtils.createAccessToken(memberEmail, now());
        RefreshToken newRefreshToken = refreshTokenRepository.save(jwtUtils.createRefreshToken(memberEmail, now()));
        return new MemberLoginResponse(accessToken, newRefreshToken);
    }
}
