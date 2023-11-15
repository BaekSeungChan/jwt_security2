package com.qortmdcks.jwt_security2.config;

import com.qortmdcks.jwt_security2.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration  // Spring 구성 클래스를 나타냄
@RequiredArgsConstructor  // Lombok: final 또는 @NonNull 필드에 대한 생성자 자동 생성
public class ApplicationConfig {
    private final UserRepository userRepository;  // UserRepository 주입

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not email"));
    }
    // 사용자 이름으로 UserDetails를 조회하는 UserDetailsService를 제공합니다.
    // 여기서는 이메일을 사용자 이름으로 사용하여 사용자를 조회합니다.

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    // 사용자 인증을 관리하는 AuthenticationProvider를 설정합니다.
    // DaoAuthenticationProvider를 사용하여 데이터베이스 기반 인증을 구현합니다.

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
    // Spring Security의 AuthenticationManager를 구성합니다.

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    // 비밀번호 암호화를 위한 PasswordEncoder를 제공합니다. BCrypt 알고리즘을 사용합니다.
}
