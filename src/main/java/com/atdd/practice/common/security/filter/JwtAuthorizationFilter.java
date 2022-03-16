package com.atdd.practice.common.security.filter;

import com.atdd.practice.common.security.infrastructure.JwtUtils;
import lombok.RequiredArgsConstructor;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestToken = request.getHeader(authorizationHeader);
        String accessToken = jwtUtils.parseToken(extractAccessToken(requestToken));


        filterChain.doFilter(request, response);
    }

    private String extractAccessToken(String token) {
        return token.substring(token.indexOf(tokenType + " "));
    }
}
