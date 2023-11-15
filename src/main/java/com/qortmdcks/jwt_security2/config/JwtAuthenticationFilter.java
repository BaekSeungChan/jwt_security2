package com.qortmdcks.jwt_security2.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  // Spring의 컴포넌트로 정의
@RequiredArgsConstructor  // Lombok: final 또는 @NonNull 필드에 대한 생성자 자동 생성
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;  // JwtService 주입

    // HTTP 요청을 필터링하여 JWT 인증 처리
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");  // Authorization 헤더에서 토큰 추출
        final String jwt;
        final String userEmail;

        // Authorization 헤더가 없거나 Bearer 토큰이 아닌 경우, 필터 체인을 계속 진행
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);  // Bearer 다음의 문자열을 JWT로 파싱
        userEmail = jwtService.extractUsername(jwt);  // JWT에서 사용자 이메일(사용자명) 추출
    }
}
