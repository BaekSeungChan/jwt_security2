package com.qortmdcks.jwt_security2.auth;

import com.qortmdcks.jwt_security2.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service  // Spring의 서비스 컴포넌트로 정의
@RequiredArgsConstructor  // Lombok: final 또는 @NonNull 필드에 대한 생성자 자동 생성
public class AuthService {
    private final UserRepository userRepository;  // UserRepository 주입

    public AuthResponse register(RegisterRequest request){
        return null;
    }
    // 회원가입 로직을 수행하는 메소드. 현재 구현되지 않음

    public AuthResponse login(LoginRequest request){
        return null;
    }
    // 로그인 로직을 수행하는 메소드. 현재 구현되지 않음
}
// 인증 서비스 로직을 담당하는 클래스
