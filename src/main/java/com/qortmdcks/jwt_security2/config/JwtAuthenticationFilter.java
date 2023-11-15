package com.qortmdcks.jwt_security2.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  // Spring의 컴포넌트로 정의
@RequiredArgsConstructor  // Lombok: final 또는 @NonNull 필드에 대한 생성자 자동 생성
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;  // JwtService 주입
    private final UserDetailsService userDetailsService;  // Spring Security의 UserDetailsService 주입

    // HTTP 요청을 필터링하여 JWT 인증 처리
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");  // Authorization 헤더에서 토큰 추출

        // Authorization 헤더가 없거나 Bearer 토큰이 아닌 경우, 필터 체인을 계속 진행
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);  // Bearer 다음의 문자열을 JWT로 파싱
        final String userEmail = jwtService.extractUsername(jwt);  // JWT에서 사용자 이메일(사용자명) 추출

        // SecurityContext에 인증 정보가 없고, 사용자 이메일이 null이 아닌 경우
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);  // 사용자 정보 로드

            // JWT가 유효한 경우
            if(jwtService.isTokenValid(jwt, userDetails)){
                // 사용자 인증 토큰 생성 및 설정
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);  // SecurityContext에 인증 정보 저장
            }
        }

        filterChain.doFilter(request, response);  // 필터 체인 계속 진행
    }
}
