package com.atdd.practice.common.security.filter;

import com.atdd.practice.common.security.infrastructure.JwtUtils;
import com.atdd.practice.member.application.MemberLoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final String authorizationHeader;

    private final String tokenType;

    private final JwtUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = request.getHeader(authorizationHeader);
        if (haveTokenInRequest(accessToken)) {
            String email = jwtUtils.parseToken(extractAccessToken(accessToken));

            MemberLoginInfo memberLoginInfo = (MemberLoginInfo) userDetailsService.loadUserByUsername(email);

            setAuthenticationInSecurityContextHolder(memberLoginInfo);
        }

        filterChain.doFilter(request, response);
    }

    private boolean haveTokenInRequest(String accessToken) {
        return StringUtils.hasText(accessToken);
    }

    private void setAuthenticationInSecurityContextHolder(MemberLoginInfo memberLoginInfo) {
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                memberLoginInfo,
                                null,
                                memberLoginInfo.getAuthorities()));
    }

    private String extractAccessToken(String token) {
        if (token == null) {
            return "";
        }
        return token.substring(token.indexOf(tokenType + " "));
    }
}
