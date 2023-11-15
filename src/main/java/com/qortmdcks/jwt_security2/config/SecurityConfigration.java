package com.qortmdcks.jwt_security2.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // Spring 구성 클래스를 나타냄
@EnableWebSecurity  // Web 보안 활성화
@EnableMethodSecurity  // 메소드 레벨 보안 활성화
@RequiredArgsConstructor  // Lombok: final 또는 @NonNull 필드에 대한 생성자 자동 생성
public class SecurityConfigration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;  // JWT 인증 필터
    private final AuthenticationProvider authenticationProvider;  // 사용자 인증 제공자

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()  // CSRF 보호 비활성화
                .authorizeHttpRequests()
                .requestMatchers("")
                .permitAll()
                .anyRequest()
                .authenticated()  // 모든 요청은 인증을 필요로 함
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션을 사용하지 않는 상태로 설정
                .and()
                .authenticationProvider(authenticationProvider)  // 사용자 인증 제공자 설정
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // JWT 인증 필터 추가
                .logout();  // 로그아웃 구성

        return http.build();  // HttpSecurity 구성 반환
    }
}
